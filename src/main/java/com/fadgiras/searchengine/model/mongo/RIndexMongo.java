package com.fadgiras.searchengine.model.mongo;

import com.fadgiras.searchengine.model.mongo.BookMongo;
import jakarta.persistence.Id;

import java.util.List;

public class RIndexMongo {

    @Id
    private String word;

    private List<BookMongo> books;

    public RIndexMongo() {
    }

    public RIndexMongo(String word, List<BookMongo> books) {
        this.word = word;
        this.books = books;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<BookMongo> getBooks() {
        return books;
    }

    public void setBooks(List<BookMongo> books) {
        this.books = books;
    }

    public void addBook(BookMongo document) {
        books.add(document);
    }

    public void removeDocumentId(int documentId) {
        books.remove(documentId);
    }

    public boolean containsDocumentId(int documentId) {
        return books.contains(documentId);
    }

    public int getDocumentCount() {
        return books.size();
    }

    @Override
    public String toString() {
        return "RIndex{" +
                "word='" + word + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RIndexMongo rIndex = (RIndexMongo) obj;
        return word.equals(rIndex.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

}
