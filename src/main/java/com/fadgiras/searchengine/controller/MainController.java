package com.fadgiras.searchengine.controller;

import com.fadgiras.searchengine.dto.BookCardDTO;
import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.Index;
import com.fadgiras.searchengine.model.RIndex;
import com.fadgiras.searchengine.repository.BookRepository;
import com.fadgiras.searchengine.repository.IndexRepository;
import com.fadgiras.searchengine.repository.RIndexRepository;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MainController {

    @Autowired
    RIndexRepository RIndexRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    IndexRepository indexRepository;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private CharArraySet stopWords = new CharArraySet(EnglishAnalyzer.getDefaultStopSet(), false);

    //Will use this in future versions to score properly, for now, we will use homebrewed scoring
    //ClassicSimilarity similarity = new ClassicSimilarity();

    Path path = Paths.get("src/main/resources/test");

    public List<String> stem(String term) {
        try {
            stopWords.add("gutenberg");
            stopWords.add("ebook");
            stopWords.add("ebooks");
            stopWords.add("project");
            stopWords.add("www");

            Analyzer analyzer = new StandardAnalyzer();
            TokenStream result = analyzer.tokenStream(null, term);
            result = new PorterStemFilter(result);
            result = new StopFilter(result, stopWords);
            CharTermAttribute resultAttr = result.addAttribute(CharTermAttribute.class);
            result.reset();

            List<String> tokens = new ArrayList<>();
            while (result.incrementToken()) {
                tokens.add(resultAttr.toString());
            }
            return tokens;
        } catch (Exception e) {
            return null;
        }
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
    public List<BookCardDTO> search(@RequestParam(value = "q", required = false) String query) {
        //TODO make a fail safe for stop words stemming
        if (query == null) {
            return new ArrayList<>();
        }else {
            query = stem(query).get(0) != null ? stem(query).get(0) : "";
//            System.err.println("query: " + query);
            List<BookCardDTO> foundBooks = new ArrayList<>();
            List<Book> books = indexRepository.findBooksByWord(query);
//            System.err.println("books: " + books.size());

            Map<BookCardDTO, Double> scores = new HashMap<>();
            Double idf = idf(query, books);
//            System.err.println("idf: " + idf);
            for (Book book : books) {
                Double tf = tf(book, query);
                Double tfidf = tf * idf;
                scores.put(new BookCardDTO(book), tfidf);
                //System.err.println(scores.values());
            }
            //sort the scores
            scores.entrySet().stream()
                    .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                    .forEach(k -> foundBooks.add(k.getKey()));
            return foundBooks;
        }
    }

    @RequestMapping(value = "/rindex", produces = "application/json")
    public String rindex() throws Exception {
        //get index from database
        List<RIndex> currentRIndexes = RIndexRepository.findAll();
        //get books from database
        List<Book> books = bookRepository.findAll();
        for(Book book : books) {
            System.err.println("processing book: " + book.getTitle());
            List<String> tokens = stem(book.getContent());
            for (String s : tokens) {
                RIndex rIndex = new RIndex(s, new ArrayList<>(Set.of(book)));
                if(currentRIndexes.contains(rIndex)) {
                    RIndex r = currentRIndexes.get(currentRIndexes.indexOf(rIndex));
                    if (!r.getBooks().contains(book)) {
                        r.addBook(book);
                    }
                } else {
                    currentRIndexes.add(rIndex);
                }
            }
        }

//        System.err.println(currentRIndexes);
        RIndexRepository.saveAll(currentRIndexes.stream().toList());
        return "ok";
    }

    @RequestMapping(value = "/index", produces = "application/json")
    public String index() {
        //get index from database
        List<Index> currentIndexes = indexRepository.findAll();
        //get books from database
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            List<String> tokens = new ArrayList<>();
            try {
                tokens = stem(book.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String s : tokens) {
                Index index = new Index(book, s, 1);
                if (currentIndexes.contains(index)) {
                    Index i = currentIndexes.get(currentIndexes.indexOf(index));
                    i.setFrequency(i.getFrequency() + 1);
                } else {
                    currentIndexes.add(index);
                }
            }
        }
        indexRepository.saveAll(currentIndexes.stream().toList());

        return "ok";
    }

    @RequestMapping(value = "/indexer", produces = "application/json")
    public String indexer() {

        //get books from database
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            //get index from database
            List<Index> allIndexes = indexRepository.getIndexByBook(book);
//            List<RIndex> allRIndexes = RIndexRepository.findAll();


            List<Index> currentIndexes = new ArrayList<>();
            List<RIndex> currentRIndexes = new ArrayList<>();


            logger.info("processing book: " + book.getTitle());
            List<String> tokens = new ArrayList<>();
            try {
                logger.info("stemming book: " + book.getTitle());
                tokens = stem(book.getContent());
                logger.info("stemmed book: " + book.getTitle());
                logger.info("tokens: " + tokens.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("processing words");
            for (String s : tokens) {
//                logger.info("processing word: " + s);
                Index index = new Index(book, s, 1);
//                RIndex rIndex = new RIndex(s, new ArrayList<>(Set.of(book)));


//                logger.info("Pre exists");
                Boolean exists = allIndexes.contains(index);
//                Boolean exists = indexRepository.existsByWordAndBook(book, s)!=null ? indexRepository.existsByWordAndBook(book, s) : false;
//                Boolean rexists = RIndexRepository.existsByWord(s)!=null ? RIndexRepository.existsByWord(s) : false;
//                logger.info("Post exists");

//                logger.info(exists.toString());
//                logger.info(rexists.toString());

                if (exists) {
//                    logger.info("Exist");
//                    System.err.println(currentIndexes);
                    Index i = allIndexes.get(allIndexes.indexOf(index));
                    i.setFrequency(i.getFrequency() + 1);
//                    logger.info(Integer.valueOf(i.getFrequency()).toString());
//                    logger.info(Integer.valueOf(allIndexes.get(allIndexes.indexOf(i)).getFrequency()).toString());
                } else {
                    allIndexes.add(index);
                }
//                if(rexists) {
//                    logger.info("RExist");
//                    RIndex r = allRIndexes.get(allRIndexes.indexOf(rIndex));
//                    if (!r.getBooks().contains(book)) {
//                        r.addBook(book);
//                    }
//                } else {
//                    allRIndexes.add(rIndex);
//                }
            }
            logger.info("processed words");
            logger.info("saving indexes");
//            RIndexRepository.saveAll(allRIndexes.stream().toList());
            indexRepository.saveAll(allIndexes.stream().toList());
            logger.info("saved indexes");
        }


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
        updateNumberOfTokens();
        indexer();
        return "ok";
    }

    @RequestMapping(value = "/rindexes", produces = "application/json")
    public List<RIndex> rindexes() {
        return RIndexRepository.findAll();
    }

    @RequestMapping(value = "/tokens", produces = "application/json")
    public String updateNumberOfTokens() throws Exception {
        //get index from database
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            List<String> tokens = stem(book.getContent());
            book.setTokens(tokens.size());
        }
        bookRepository.saveAll(books);
        return "ok";
    }

    @RequestMapping(value = "/tf", produces = "application/json")
    public Double getTf(@RequestParam(value = "book") String bookTitle, @RequestParam(value = "word") String word) throws Exception {
        String stemmedWord = stem(word).get(0);
        Book book = bookRepository.getBookByTitle(bookTitle);
        Index index = indexRepository.findIndexByBookAndWord(book, stemmedWord);
        if (index == null) {
            return 0.0;
        }
        return (double) index.getFrequency() / book.getTokens();
    }

    public Double tf(Book book, String word) {
        //tf(t,d) = n/N
        //n is the number of times term t appears in the document d.
        //N is the total number of terms in the document d.

        int n = indexRepository.findIndexByBookAndWord(book, word).getFrequency();
        int N = book.getTokens();

        return (double) n / N;
    }

    public Double idf(String word, List<Book> books) {
        //idf(t,D) = log (N/( n))
        //N is the number of documents in the data set.
        //n is the number of documents that contain the term t among the data set.
        int N = bookRepository.findAll().size();
//        System.err.println("N: " + N);
        int n = indexRepository.findBooksByWord(word).size();
//        System.err.println("n: " + n);

        return Math.log(N / n);
    }

    public Double tfidf(Book book, String word, List<Book> books) {
        return tf(book, word) * idf(word, books);
    }

    //regex -> list words -> search books containing words -> tfidf -> top 5
}
