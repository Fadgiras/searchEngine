package com.fadgiras.searchengine.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class IndexMongo {

    @Id
    private IndexMongoId id;
    private int frequency;

    public IndexMongo() {
    }

    public IndexMongo(BookMongo book, String word, int frequency) {
        this.id = new IndexMongoId(book, word);
        this.frequency = frequency;
    }

    public IndexMongoId getId() {
        return id;
    }

    public void setId(IndexMongoId id) {
        this.id = id;
    }

    public BookMongo getBook() {
        return id.getBook();
    }

    public void setBook(BookMongo book) {
        this.id.setBook(book);
    }

    public String getWord() {
        return id.getWord();
    }

    public void setWord(String word) {
        this.id.setWord(word);
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Index{" +
                "bookId=" + id.getBook() +
                ", word='" + id.getWord() + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
