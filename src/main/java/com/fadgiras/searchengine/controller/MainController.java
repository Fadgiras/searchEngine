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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Map<String,List<Integer>> stem(@RequestParam(value = "term", required = false) String term) {
        //get index from database
        List<Index> indexes = indexRepository.getAllIndexes();
        //get document id from database
        Book book = new Book("title", "author", """
                To Dr. E. A. Wallis Budge my thanks are due, as he suggested that I
                should write these histories, and he has given me the benefit of his
                advice. To him, as to Sir Frederic Kenyon and Mr. D. G. Hogarth, I
                am indebted for permission to make use of illustrations, which have
                appeared in official publications of the British Museum. My thanks
                are also due to Monsieur Ernest Leroux of Paris for allowing me to
                reproduce some of the plates from the "Mémoires de la Délégation en
                Perse," published by him under the editorship of Monsieur J. de Morgan;
                and to the Council and Secretary of the Society of Biblical Archæology
                for the loan of a block employed to illustrate a paper I contributed to
                their Proceedings. The greater number of the plates illustrating the
                excavations are from photographs taken on the spot; and the plans and
                drawings figured in the text are the work of Mr. E. J. Lambert and Mr.
                C. O. Waterhouse, who have spared no pains to ensure their accuracy.
                The designs upon the cover of this volume represent the two most
                prominent figures in Babylonian tradition. In the panel on the face of
                the cover the national hero Gilgamesh is portrayed, whose epic reflects
                the Babylonian heroic ideal. The panel on the back of the binding
                contains a figure of Marduk, the city-god of Babylon, grasping in his
                right hand the flaming sword with which he severed the dragon of chaos.
                                
                L. W. KING.
                                
                                
                                
                                
                CONTENTS
                                
                    CHAPTER I
                                
                    INTRODUCTORY: BABYLON'S PLACE IN THE HISTORY OF ANTIQUITY
                                
                                
                    Babylon as a centre of civilization--Illustrations
                    of foreign influence--Babylon's share in the origin
                    of the culture she distributed--Causes which led to
                    her rise as capital--Advantages of her geographical
                    position--Transcontinental lines of traffic--The Euphrates
                    route, the Royal Road, and the Gates of Zagros--Her
                    supremacy based on the strategic and commercial qualities
                    of her site--The political centre of gravity in Babylonia
                    illustrated by the later capitals, Seleucia, Ctesiphon,
                    and Baghdad--The Persian Gulf as barrier, and as channel
                    of international commerce--Navigation on the Euphrates and
                    the Tigris--Causes of Babylon's deposition--Her treatment
                    by Cyrus, Alexander, and Seleucus--The Arab conquest of
                    Mesopotamia instructive for comparison with the era of
                    early city-states--Effect of slackening of international
                    communications--Effect of restoration of commercial
                    intercourse with the West--Three main periods of Babylon's
                    foreign influence--Extent to which she moulded the cultural
                    development of other races-Traces of contact in Hebrew
                    religion and in Greek mythology--Recent speculation on the
                    subject to be tested by the study of history
                """);
        // test data
        book.setId(1);

        //TODO Know how to store and retrieve content from database, the lob isn't working
        System.err.println(bookRepository.getBookById(1).getContent());
        //stem term
//        try {
//            List<String> stemmed = testStem(term);
//            for (String s : stemmed) {
//                //get all keys
//            }
//            return testStem(term);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }

        return null;
    }
}
