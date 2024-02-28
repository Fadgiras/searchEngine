package com.fadgiras.searchengine.model.idclass;

import java.io.Serializable;

public class JaccardBookId implements Serializable {
    private Long book1;
    private Long book2;

    public JaccardBookId() {
    }

    public JaccardBookId(Long book1, Long book2) {
        this.book1 = book1;
        this.book2 = book2;
    }

    public Long getBook1() {
        return book1;
    }

    public void setBook1(Long book1) {
        this.book1 = book1;
    }

    public Long getBook2() {
        return book2;
    }

    public void setBook2(Long book2) {
        this.book2 = book2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JaccardBookId that = (JaccardBookId) o;

        if (book1 != null ? !book1.equals(that.book1) : that.book1 != null) return false;
        return book2 != null ? book2.equals(that.book2) : that.book2 == null;
    }

    @Override
    public int hashCode() {
        int result = book1 != null ? book1.hashCode() : 0;
        result = 31 * result + (book2 != null ? book2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JaccardBookId{" +
                "book1=" + book1 +
                ", book2=" + book2 +
                '}';
    }
}
