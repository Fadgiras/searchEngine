package com.fadgiras.searchengine.controller;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    public List<String> testStem(String term) throws Exception {
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

    //TODO Use lucene PorterStemmer
//    public String testStem2 (String term) {
//        PorterStemmer stemmer = new PorterStemmer();
//        return stemmer.stem(term);
//    }

    @RequestMapping(value = "/search", produces = "application/json")
    public String search(@RequestParam(value = "q", required = false) String query) {
        return "You searched for: " + query;
    }

    @RequestMapping(value = "/stem", produces = "application/json")
    public List<String> stem(@RequestParam(value = "term", required = false) String term) {
        try {
            return testStem(term);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
