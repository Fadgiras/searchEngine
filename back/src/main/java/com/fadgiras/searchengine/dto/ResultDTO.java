package com.fadgiras.searchengine.dto;

import java.util.List;

public class ResultDTO {

    private List<BookCardDTO> books;
    private List<BookCardDTO> suggestedBooks;

    public ResultDTO(List<BookCardDTO> books, List<BookCardDTO> suggestedBooks) {
        this.books = books;
        this.suggestedBooks = suggestedBooks;
    }

    public ResultDTO() {
    }

    public List<BookCardDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookCardDTO> books) {
        this.books = books;
    }

    public List<BookCardDTO> getSuggestedBooks() {
        return suggestedBooks;
    }

    public void setSuggestedBooks(List<BookCardDTO> suggestedBooks) {
        this.suggestedBooks = suggestedBooks;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "books=" + books +
                ", suggestedBooks=" + suggestedBooks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultDTO resultDTO = (ResultDTO) o;

        if (books != null ? !books.equals(resultDTO.books) : resultDTO.books != null) return false;
        return suggestedBooks != null ? suggestedBooks.equals(resultDTO.suggestedBooks) : resultDTO.suggestedBooks == null;
    }

    @Override
    public int hashCode() {
        int result = books != null ? books.hashCode() : 0;
        result = 31 * result + (suggestedBooks != null ? suggestedBooks.hashCode() : 0);
        return result;
    }

}
