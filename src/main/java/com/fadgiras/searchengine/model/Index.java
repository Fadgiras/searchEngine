package com.fadgiras.searchengine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "Index")
public class Index {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @Column(name = "word", columnDefinition = "TEXT")
    private String word;

    @Column(name = "frequency")
    private int frequency;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


}
