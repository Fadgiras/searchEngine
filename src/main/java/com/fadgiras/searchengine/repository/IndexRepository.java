package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IndexRepository extends JpaRepository<Index, Book> {

    @Query("SELECT i FROM Index i WHERE i.word = ?1")
    Index getIndexByWord(String word);

    @Query("SELECT i FROM Index i WHERE i.book = ?1")
    Index getIndexByBook(Book book);

    @Query("SELECT i FROM Index i WHERE i.book = ?1 AND i.word = ?2")
    Index getIndexByBookAndWord(Book book, String word);
}