package com.fadgiras.searchengine;

import com.fadgiras.searchengine.repository.mongo.BookMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = BookMongoRepository.class)
public class SearchengineApplication {

	@Autowired
	BookMongoRepository bookMongoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SearchengineApplication.class, args);
	}

}
