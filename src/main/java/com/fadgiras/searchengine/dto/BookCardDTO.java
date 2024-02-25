package com.fadgiras.searchengine.dto;

import com.fadgiras.searchengine.model.Book;

public class BookCardDTO {
    private Long id;
    private String title;
    private String author;

    public BookCardDTO() {
    }

    public BookCardDTO(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public BookCardDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookCardDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
