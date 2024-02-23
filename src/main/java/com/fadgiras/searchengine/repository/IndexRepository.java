package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndexRepository extends JpaRepository<Index, Book> {

    @Query("SELECT i FROM Index i WHERE i.word = ?1")
    Index getIndexByWord(String word);

    @Query("SELECT i FROM Index i WHERE i.book = ?1")
    List<Index> getIndexByBook(Book book);

    @Query("SELECT i FROM Index i WHERE i.book = ?1 AND i.word = ?2")
    List<Index> findIndexByBookAndWord(Book book, String word);

    @Query("SELECT true FROM Index i WHERE i.book = ?1 AND i.word = ?2")
    Boolean existsByWordAndBook(Book book, String word);

    @Query("SELECT i FROM Index i WHERE i.word = ?1")
    List<Index> findByWord(String word);

    //get all books that contain the word
    @Query("SELECT i.book FROM Index i WHERE i.word = ?1")
    List<Book> findBooksByWord(String word);
}