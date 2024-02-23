package com.fadgiras.searchengine.repository.mongo;

import com.fadgiras.searchengine.model.mongo.BookMongo;
import com.fadgiras.searchengine.model.mongo.IndexMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IndexMongoRepository extends MongoRepository<IndexMongo, String> {
    List<IndexMongo> findByWord(String word);
    List<IndexMongo> findByFrequency(int frequency);
    List<IndexMongo> findByBookAndWord(String book, String word);
    Boolean existsByWordAndBook(BookMongo book, String word);
}
