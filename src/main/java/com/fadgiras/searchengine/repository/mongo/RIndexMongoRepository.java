package com.fadgiras.searchengine.repository.mongo;

import com.fadgiras.searchengine.model.mongo.RIndexMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RIndexMongoRepository extends MongoRepository<RIndexMongo, String> {
    RIndexMongo findByWord(String word);
    Boolean existsByWord(String word);
}

