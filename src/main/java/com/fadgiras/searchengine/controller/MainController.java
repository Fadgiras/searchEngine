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

//UWU
import java.util.regex.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


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
        // result = new PorterStemFilter(result);
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

        //// test data
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("gutenberg.txt");
        String content = new String(is.readAllBytes());
        Book book = new Book("title", "author", content);
        book.setId(1);
        bookRepository.save(book);

        //System.err.println(bookRepository.getBookById(1).getContent());
        //stem term
        List<String> tokens = testStem(bookRepository.getBookById(1).getContent());
        //System.err.println(tokens.size());

        for (String s : tokens) {
            if (currentIndexes.contains(new Index(s, null))){
                //System.err.println("word found");
                Index index = currentIndexes.get(currentIndexes.indexOf(new Index(s, null)));
                if (!index.containsDocumentId(book.getId())){
                    //System.err.println("book not found in index");
                    index.addDocumentId(book);
                    indexes.add(index);
                }
            }else {
                //System.err.println("word not found");
                indexes.add(new Index(s, Set.of(book)));
            }
        }

        indexRepository.saveAll(indexes);

        //try {
            ////List<String> stemmed = testStem(bookRepository.getBookById(1).getContent());
            ////for (String s : stemmed) {
                //////get all keys
            ////}
            //return testStem(bookRepository.getBookById(1).getContent());
        //} catch (Exception e) {
            //e.printStackTrace();
            //return new ArrayList<>();
        //}
        return "ok";
    }

    //uWu

    @Autowired
    public MainController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/search/or", produces = "application/json")
    public List<Book> searchOr(@RequestParam(value = "term1", required = false) String term1, @RequestParam(value = "term2", required = false) String term2) {
        if (term1 == null || term1.equals("") || term2 == null || term2.equals("")) {
            return Collections.emptyList();
        } else {
            List<Book> matchingBooks = bookRepository.findByContentRegex(term1, term2);
            return matchingBooks;
        }
    }
    
    @RequestMapping(value = "/search/and", produces = "application/json")
    public List<Book> searchAnd(@RequestParam(value = "term1", required = false) String term1, @RequestParam(value = "term2", required = false) String term2) {
        if (term1 == null || term1.equals("") || term2 == null || term2.equals("")) {
            return Collections.emptyList();
        } else {
            List<Book> matchingBooks = bookRepository.findByContentTwoTerms(term1, term2);
            return matchingBooks;
        }
    }
    
    @RequestMapping(value = "/search/exact", produces = "application/json")
    public List<Book> searchExact(@RequestParam(value = "term", required = false) String term) {
        if (term == null || term.equals("")) {
            return Collections.emptyList();
        } else {
            List<Book> matchingBooks = bookRepository.findByExactWord(term);
            return matchingBooks;
        }
    }
    
    @RequestMapping(value = "/search/prefix", produces = "application/json")
    public List<Book> searchPrefix(@RequestParam(value = "prefix", required = false) String prefix) {
        if (prefix == null || prefix.equals("")) {
            return Collections.emptyList();
        } else {
            List<Book> matchingBooks = bookRepository.findByContentPrefix(prefix);
            return matchingBooks;
        }
    }

    @RequestMapping(value = "/regex", produces = "application/json")
    public String regex(@RequestParam(value = "term", required = false) String term) {
        if (term == null || term.equals("")) {
            return null;
        }else {
            System.out.println("Matching books: ");
            return "blep " + term;
        }
    }

    @RequestMapping(value = "/regexSearch", produces = "application/json")
    public List<Book> regexSearch(@RequestParam(value = "term", required = false) String term) {
        System.out.println("Term: " + term);

        if (term == null || term.equals("")) {
            return Collections.emptyList();
        } else {
            // Obtenez tous les livres de la base de données
            List<Book> allBooks = bookRepository.findAll();
            System.out.println("All books: " + allBooks);

            // Créez un motif à partir du terme de recherche
            Pattern pattern = Pattern.compile(term);

            // Filtrez les livres dont le contenu correspond au motif
            List<Book> matchingBooks = allBooks.stream()
                .filter(book -> {
                    Matcher matcher = pattern.matcher(book.getContent());
                    boolean matches = matcher.find();
                    System.out.println("Book: " + book + " matches: " + matches);
                    return matcher.find();
                })
                .collect(Collectors.toList());

            System.out.println("Matching books: " + matchingBooks);
            return matchingBooks;
        }
    }

    @RequestMapping(value = "/regret", produces = "application/json")
    public List<String> regret(@RequestParam(value = "term", required = false) String term) {
        System.out.println("Term: " + term);

        if (term == null || term.equals("")) {
            return Collections.emptyList();
        } else {
            // Recherchez les livres dans la base de données qui correspondent au terme de recherche
            List<Book> matchingBooks = bookRepository.findByContentContaining(term);
            System.out.println("Matching books: " + matchingBooks);

            // Créez un motif à partir du terme de recherche
            Pattern pattern = Pattern.compile("\\b" + term + "\\b");

            // Créez une liste pour stocker les mots clés trouvés
            List<String> keywords = new ArrayList<>();

            // Parcourez chaque livre correspondant
            for (Book book : matchingBooks) {
                // Parcourez chaque mot dans le contenu du livre
                for (String word : book.getContent().split("\\s+")) {
                    // Si le mot correspond au motif, ajoutez-le à la liste des mots clés
                    Matcher matcher = pattern.matcher(word);
                    if (matcher.find()) {
                        keywords.add(word);
                    }
                }
            }

            return keywords;
        }
    }

    @RequestMapping(value = "/regexSearcher", produces = "application/json")
    public List<Book> regexSearcher(@RequestParam(value = "term", required = false) String term) {
        System.out.println("Term: " + term);
    
        if (term == null || term.equals("")) {
            return Collections.emptyList();
        } else {
            // Recherchez les livres dans la base de données qui correspondent au terme de recherche
            List<Book> matchingBooks = bookRepository.findByContentRegex(term);
            System.out.println("Matching books: " + matchingBooks.size());
    
            // Créez un motif à partir du terme de recherche
            Pattern pattern = Pattern.compile("\\b" + term + "\\b");
    
            // Créez une liste pour stocker les mots clés trouvés
            List<String> keywords = new ArrayList<>();
    
            // Parcourez chaque livre correspondant
            for (Book book : matchingBooks) {
                // Parcourez chaque mot dans le contenu du livre
                for (String word : book.getContent().split("\\s+")) {
                    // Si le mot correspond au motif, ajoutez-le à la liste des mots clés
                    Matcher matcher = pattern.matcher(word);
                    if (matcher.find()) {
                        keywords.add(word);
                    }
                }
            }
    
            return matchingBooks;
        }
    }

    @RequestMapping(value = "/extractWords", produces = "application/json")
    public List<String> extractWords(@RequestParam(required = false) String text, @RequestParam(required = false) String regex) {
        // Compiler l'expression régulière en un Pattern
        Pattern pattern = Pattern.compile(regex);

        // Créer un Matcher à partir du Pattern
        Matcher matcher = pattern.matcher(text);

        // Liste pour stocker les correspondances
        List<String> matches = new ArrayList<>();

        // Trouver et ajouter toutes les correspondances à la liste
        while (matcher.find()) {
            matches.add(matcher.group());
        }

        // Afficher le résultat
        System.out.println("La chaîne de caractères correspond-elle à l'expression régulière ? " + matches);

        // Retourner la liste des correspondances
        return matches;
    }

    @RequestMapping(value = "/booksWithRegex", produces = "application/json")
    public List<Book> getBooksWithRegex(@RequestParam String regex) {
        return bookRepository.findBooksByTitleWithRegex(regex);
    }
}
