package com.fadgiras.searchengine.model.mongo;

import org.springframework.data.annotation.Id;

public class BookMongo {

    @Id
    private String id;
    private String title;
    private String author;
    private String content;

    public BookMongo() {
    }

    public BookMongo(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
