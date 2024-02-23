package com.fadgiras.searchengine.model.mongo;

public class IndexMongoId {
private BookMongo book;
    private String word;

    public IndexMongoId() {
    }

    public IndexMongoId(BookMongo book, String word) {
        this.book = book;
        this.word = word;
    }

    public BookMongo getBook() {
        return book;
    }

    public void setBook(BookMongo book) {
        this.book = book;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
