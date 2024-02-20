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
import java.util.stream.Stream;

@RestController
public class MainController {

    @Autowired
    RIndexRepository RIndexRepository;

    @Autowired
    BookRepository bookRepository;

    Path path = Paths.get("src/main/resources/test");

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

    public String getAuthor(String content) {
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.contains("Author:")) {
                return line.split(":")[1];
            } else if (line.contains("START OF THE PROJECT GUTENBERG")) {
                return "Unknown";
            }
        }
        return "Unknown";
    }

    @RequestMapping(value = "/search", produces = "application/json")
    public String search(@RequestParam(value = "q", required = false) String query) {
        return "You searched for: " + query;
    }

    @RequestMapping(value = "/rindex", produces = "application/json")
    public String rindex() throws Exception {
        //get index from database
        List<RIndex> currentRIndexes = RIndexRepository.getAllIndexes();
        //get books from database
        List<Book> books = bookRepository.findAll();
        for(Book book : books) {
            System.err.println("processing book: " + book.getTitle());
            List<String> tokens = stem(book.getContent());
            for (String s : tokens) {
                RIndex rIndex = new RIndex(s, new ArrayList<>(Set.of(book)));
                if(currentRIndexes.contains(rIndex)) {
                    RIndex r = currentRIndexes.get(currentRIndexes.indexOf(rIndex));
                    r.getBooks().add(book);
                } else {
                    currentRIndexes.add(rIndex);
                }
            }
        }

        System.err.println(currentRIndexes);
        RIndexRepository.saveAll(currentRIndexes.stream().toList());
        return "ok";
    }

    @RequestMapping(value = "/refreshBooks", produces = "application/json")
    public String refreshBooks() {
        //get path of src folder
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
                // split the extension from the file name
                book = book.split("\\.")[0];
                // get line containing "Author:"
                String author = getAuthor(content);
                books.add(new Book(book, author, content));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookRepository.saveAll(books);
        return "ok";
    }

    @RequestMapping(value = "/init", produces = "application/json")
    public String init() throws Exception {
        refreshBooks();
        rindex();
        return "ok";
    }

    @RequestMapping(value = "/rindexes", produces = "application/json")
    public List<RIndex> rindexes() {
        return RIndexRepository.getAllIndexes();
    }

}
