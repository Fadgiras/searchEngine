package com.fadgiras.searchengine.model;

import com.fadgiras.searchengine.model.idclass.IndexId;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "Index")
@IdClass(IndexId.class)
public class Index {

    public Index() {
    }

    public Index(Book book, String word, int frequency) {
        this.book = book;
        this.word = word;
        this.frequency = frequency;
    }

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    Book book;

    @Id
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

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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
                "book=" + book +
                ", word='" + word + '\'' +
                ", frequency=" + frequency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Index)) return false;
        Index index = (Index) o;
        return getFrequency() == index.getFrequency() && getBook().equals(index.getBook()) && getWord().equals(index.getWord());
    }

    @Override
    public int hashCode() {
        int result = getBook().hashCode();
        result = 31 * result + getWord().hashCode();
        result = 31 * result + getFrequency();
        return result;
    }


}
