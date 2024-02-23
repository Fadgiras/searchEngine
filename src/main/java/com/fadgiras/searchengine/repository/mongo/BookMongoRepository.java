package com.fadgiras.searchengine.repository.mongo;

import com.fadgiras.searchengine.model.mongo.BookMongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookMongoRepository extends MongoRepository<BookMongo, String> {
    BookMongo findByTitle(String title);
    List<BookMongo> findByAuthor(String author);
    BookMongo findByContent(String content);
}