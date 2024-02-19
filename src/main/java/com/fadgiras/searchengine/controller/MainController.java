package com.fadgiras.searchengine.controller;

import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.RIndex;
import com.fadgiras.searchengine.repository.BookRepository;
import com.fadgiras.searchengine.repository.RIndexRepository;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class MainController {

    @Autowired
    RIndexRepository RIndexRepository;

    @Autowired
    BookRepository bookRepository;

    public List<String> stem(String term) throws Exception {
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream result = analyzer.tokenStream(null, term);
        result = new PorterStemFilter(result);
        result = new StopFilter(result, EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
        CharTermAttribute resultAttr = result.addAttribute(CharTermAttribute.class);
        result.reset();

        List<String> tokens = new ArrayList<>();
        while (result.incrementToken()) {
            tokens.add(resultAttr.toString());
        }
        return tokens;
    }

    @RequestMapping(value = "/search", produces = "application/json")
    public String search(@RequestParam(value = "q", required = false) String query) {
        return "You searched for: " + query;
    }

    @RequestMapping(value = "/rindex", produces = "application/json")
    public String rindex(@RequestParam(value = "term", required = false) String term) throws Exception {
        //get index from database
        List<RIndex> currentRIndexes = RIndexRepository.getAllIndexes();
        List<RIndex> RIndexes = new ArrayList<>();
        //get document id from database

//        // test data
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("10000-0.txt");
        String content = new String(is.readAllBytes());
        Book book = new Book("title", "author", content);
        book.setId(1);
        bookRepository.save(book);

        //System.err.println(bookRepository.getBookById(1).getContent());
        //stem term
        List<String> tokens = stem(bookRepository.getBookById(1).getContent());
        //System.err.println(tokens.size());

        for (String s : tokens) {
            if (currentRIndexes.contains(new RIndex(s, null))) {
                //System.err.println("word found");
                RIndex RIndex = currentRIndexes.get(currentRIndexes.indexOf(new RIndex(s, null)));
                if (!RIndex.containsDocumentId(book.getId())) {
                    //System.err.println("book not found in index");
                    RIndex.addDocumentId(book);
                    RIndexes.add(RIndex);
                }
            } else {
                //System.err.println("word not found");
                RIndexes.add(new RIndex(s, Set.of(book)));
            }
        }

        RIndexRepository.saveAll(RIndexes);

        //try {
        //    List<String> stemmed = testStem(bookRepository.getBookById(1).getContent());
        //    for (String s : stemmed) {
        //get all keys
        //    }
        //    return testStem(bookRepository.getBookById(1).getContent());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //    return new ArrayList<>();
        //}
        return "ok";
    }

    @RequestMapping(value = "/refreshBooks", produces = "application/json")
    public String refreshBooks() {
        //get path of src folder
        Path path = Paths.get("src/main/resources/test");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        List<Book> books = new ArrayList<>();
        //get each file in the folder
        //for each file, get the content and save it to the database

        List<String> files =    Stream.of(new File(path.toUri()).listFiles())
                                .filter(file -> !file.isDirectory())
                                .map(File::getName).toList();

        for (String book : files) {
            try {
                InputStream is = classloader.getResourceAsStream("test/" + book);
                String content = new String(is.readAllBytes());
                // split the extension fron the file name
                book = book.split("\\.")[0];
                books.add(new Book(book, "author", content));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookRepository.saveAll(books);
        return "ok";
    }
}
