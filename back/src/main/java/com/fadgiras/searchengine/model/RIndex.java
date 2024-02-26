package com.fadgiras.searchengine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RIndex")
public class RIndex {

    @Id
    private String word;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reversed_index_book",
            joinColumns = { @JoinColumn(name = "word")},
            inverseJoinColumns = { @JoinColumn(name = "id")})
    private List<Book> books;

    public RIndex() {
        this.books = new ArrayList<>();
    }

    public RIndex(String word, List<Book> books) {
        this.word = word;
        this.books = new ArrayList<>(books);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void addBook(Book document) {
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

    public String toString() {
        return "RIndex{word='" + word + "', documentIds=" + books + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RIndex rIndex = (RIndex) obj;
        return word.equals(rIndex.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}
