package com.fadgiras.searchengine.model;

import com.fadgiras.searchengine.model.idclass.JaccardBookId;
import jakarta.persistence.*;

@Entity
@Table(name = "jaccard_book")
@IdClass(JaccardBookId.class)
public class JaccardBook {

    @Id
    @ManyToOne
    @JoinColumn(name = "book_1_id")
    private Book book1;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_2_id")
    private Book book2;

    private double jaccardDistance;

    public JaccardBook() {
    }

    public JaccardBook(Book book1, Book book2, double jaccardDistance) {
        this.book1 = book1;
        this.book2 = book2;
        this.jaccardDistance = jaccardDistance;
    }

    public Book getBook2() {
        return book2;
    }

    public void setBook2(Book book2) {
        this.book2 = book2;
    }

    public Book getBook1() {
        return book1;
    }

    public void setBook1(Book book1) {
        this.book1 = book1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JaccardBook that = (JaccardBook) o;

        if (book1 != null ? !book1.equals(that.book1) : that.book1 != null) return false;
        if (book2 != null ? !book2.equals(that.book2) : that.book2 != null) return false;
        return jaccardDistance == that.jaccardDistance;
    }

    @Override
    public int hashCode() {
        int result = book1 != null ? book1.hashCode() : 0;
        result = 31 * result + (book2 != null ? book2.hashCode() : 0);
        result = 31 * result + (int) jaccardDistance;
        return result;
    }

    @Override
    public String toString() {
        return "JaccardBook{" +
                "book1=" + book1 +
                ", book2=" + book2 +
                ", jaccardDistance=" + jaccardDistance +
                '}';
    }

}