package com.fadgiras.searchengine.model.idclass;

import java.io.Serializable;

public class IndexId implements Serializable {
    private Long book;
    private String word;

    public IndexId() {
    }

    public IndexId(Long book, String word) {
        this.book = book;
        this.word = word;
    }

    public Long getBook() {
        return book;
    }

    public void setBook(Long book) {
        this.book = book;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexId indexId = (IndexId) o;

        if (book != null ? !book.equals(indexId.book) : indexId.book != null) return false;
        return word != null ? word.equals(indexId.word) : indexId.word == null;
    }

    @Override
    public int hashCode() {
        int result = book != null ? book.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IndexId{" +
                "book=" + book +
                ", word='" + word + '\'' +
                '}';
    }

}

