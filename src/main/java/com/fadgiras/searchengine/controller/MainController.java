package com.fadgiras.searchengine.controller;

import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.Index;
import com.fadgiras.searchengine.repository.BookRepository;
import com.fadgiras.searchengine.repository.IndexRepository;
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


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class MainController {

    @Autowired
    IndexRepository indexRepository;

    @Autowired
    BookRepository bookRepository;

    public List<String> testStem(String term) throws Exception {
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream result = analyzer.tokenStream(null, term);
        //avoid stemming here
//        result = new PorterStemFilter(result);
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

    @RequestMapping(value = "/stem", produces = "application/json")
    public String stem(@RequestParam(value = "term", required = false) String term) throws Exception {
        //get index from database
        List<Index> currentIndexes = indexRepository.getAllIndexes();
        List<Index> indexes = new ArrayList<>();
        //get document id from database

//        // test data
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("gutenberg.txt");
        String content = new String(is.readAllBytes());
        Book book = new Book("title", "author", content);
        book.setId(1);
        bookRepository.save(book);

//        System.err.println(bookRepository.getBookById(1).getContent());
        //stem term
        List<String> tokens = testStem(bookRepository.getBookById(1).getContent());
//        System.err.println(tokens.size());

        for (String s : tokens) {
            if (currentIndexes.contains(new Index(s, null))){
//                System.err.println("word found");
                Index index = currentIndexes.get(currentIndexes.indexOf(new Index(s, null)));
                if (!index.containsDocumentId(book.getId())){
//                    System.err.println("book not found in index");
                    index.addDocumentId(book);
                    indexes.add(index);
                }
            }else {
//                System.err.println("word not found");
                indexes.add(new Index(s, Set.of(book)));
            }
        }

        indexRepository.saveAll(indexes);

//        try {
////            List<String> stemmed = testStem(bookRepository.getBookById(1).getContent());
////            for (String s : stemmed) {
////                //get all keys
////            }
//            return testStem(bookRepository.getBookById(1).getContent());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
        return "ok";
    }
}
