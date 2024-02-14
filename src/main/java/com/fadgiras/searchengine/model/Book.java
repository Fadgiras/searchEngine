package com.fadgiras.searchengine.model;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "book")
public class Book {
    @Id
    private int id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "author", columnDefinition = "TEXT")
    private String author;

    @Lob @Basic(fetch=LAZY)
    @Column(name = "content", columnDefinition = "CLOB")
    private String content;

}
