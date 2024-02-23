package com.fadgiras.searchengine.controller;

import com.fadgiras.searchengine.model.Index;
import com.fadgiras.searchengine.model.RIndex;
import com.fadgiras.searchengine.model.mongo.BookMongo;
import com.fadgiras.searchengine.model.mongo.IndexMongo;
import com.fadgiras.searchengine.model.mongo.RIndexMongo;
import com.fadgiras.searchengine.repository.mongo.BookMongoRepository;

import com.fadgiras.searchengine.repository.mongo.IndexMongoRepository;
import com.fadgiras.searchengine.repository.mongo.RIndexMongoRepository;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    BookMongoRepository bookMongoRepository;
    @Autowired
    IndexMongoRepository indexMongoRepository;
    @Autowired
    RIndexMongoRepository rIndexMongoRepository;


    Path path = Paths.get("src/main/resources/test");
    private static final Logger logger = LoggerFactory.getLogger(MongoController.class);

    @RequestMapping("/save")
    public String save() {
        bookMongoRepository.save(new BookMongo("Book 1", "Author 1", "Content 1"));
        bookMongoRepository.save(new BookMongo("Book 2", "Author 2", "Content 2"));
        bookMongoRepository.save(new BookMongo("Book 3", "Author 3", "Content 3"));
        return "Done";
    }

    @RequestMapping("/findall")
    public String findAll() {
        String result = "";
        List<BookMongo> books = bookMongoRepository.findAll();
        for (BookMongo book : books) {
            result += book.toString() + "<br>";
        }
        return result;
    }

    @RequestMapping("/findbyid")
    public String findById(@RequestParam("id") String id) {
        String result = "";
        BookMongo book = bookMongoRepository.findById(id).orElse(null);
        result = book != null ? book.toString() : "Not found";
        return result;
    }

    @RequestMapping("/findbytitle")
    public String fetchDataByTitle(@RequestParam("title") String title) {
        String result = "";
        BookMongo book = bookMongoRepository.findByTitle(title);
        result = book != null ? book.toString() : "Not found";
        return result;
    }

    @RequestMapping("/findbyauthor")
    public String fetchDataByAuthor(@RequestParam("author") String author) {
        String result = "";
        for (BookMongo book : bookMongoRepository.findByAuthor(author)) {
            result += book.toString() + "<br>";
        }
        return result;
    }

    @RequestMapping("/findbycontent")
    public String fetchDataByContent(@RequestParam("content") String content) {
        String result = "";
        BookMongo book = bookMongoRepository.findByContent(content);
        result = book != null ? book.toString() : "Not found";
        return result;
    }

    @RequestMapping("/deleteAll")
    public String deleteAll() {
        bookMongoRepository.deleteAll();
        return "Done";
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

    @RequestMapping(value = "/refreshBooks", produces = "application/json")
    public String refreshBooks() {
        logger.info("Refreshing books");
        //get path of src folder
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        List<BookMongo> books = new ArrayList<>();
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
                books.add(new BookMongo(book, author, content));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bookMongoRepository.saveAll(books);
        logger.info("Refreshed books");
        return "ok";
    }

    @RequestMapping(value = "/indexer", produces = "application/json")
    public String indexer() {

        //get books from database
        List<BookMongo> books = bookMongoRepository.findAll();

        for (BookMongo book : books) {
            //get index from database
            List<IndexMongo> allIndexes = indexMongoRepository.findAll();
            List<RIndexMongo> allRIndexes = rIndexMongoRepository.findAll();

            //TODO Use this to store new indexes
            List<IndexMongo> currentIndexes = new ArrayList<>();
            List<RIndexMongo> currentRIndexes = new ArrayList<>();

            System.err.println("processing book: " + book.getTitle());
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
                IndexMongo index = new IndexMongo(book, s, 1);
                RIndexMongo rIndex = new RIndexMongo(s, new ArrayList<>(Set.of(book)));


//                logger.info("Pre exists");
                Boolean exists = indexMongoRepository.existsByWordAndBook(book, s)!=null ? indexMongoRepository.existsByWordAndBook(book, s) : false;
                Boolean rexists = rIndexMongoRepository.existsByWord(s)!=null ? rIndexMongoRepository.existsByWord(s) : false;
//                logger.info("Post exists");

//                logger.info(exists.toString());
//                logger.info(rexists.toString());

                if (exists) {
//                    logger.info("Exist");
//                    System.err.println(currentIndexes);
                    IndexMongo i = allIndexes.get(allIndexes.indexOf(index));
                    i.setFrequency(i.getFrequency() + 1);
                } else {
                    allIndexes.add(index);
                }
                if(rexists) {
//                    logger.info("RExist");
                    RIndexMongo r = allRIndexes.get(allRIndexes.indexOf(rIndex));
                    if (!r.getBooks().contains(book)) {
                        r.addBook(book);
                    }
                } else {
                    allRIndexes.add(rIndex);
                }
            }
            logger.info("processed words");
            logger.info("saving indexes");
            rIndexMongoRepository.saveAll(allRIndexes.stream().toList());
            indexMongoRepository.saveAll(allIndexes.stream().toList());
            logger.info("saved indexes");
        }


        return "ok";
    }

}
