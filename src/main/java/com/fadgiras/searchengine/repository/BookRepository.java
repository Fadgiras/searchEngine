package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    //get all books
    @Query("SELECT b FROM Book b")
    List<Book> getAllBooks();

    //get book by id
    @Query("SELECT b FROM Book b WHERE b.id = ?1")
    Book getBookById(int id);

    //get book by title
    @Query("SELECT b FROM Book b WHERE b.title = ?1")
    Book getBookByTitle(String title);

    //get book by author
    @Query("SELECT b FROM Book b WHERE b.author = ?1")
    Book getBookByAuthor(String author);

    //get book by content
    @Query("SELECT b FROM Book b WHERE b.content = ?1")
    Book getBookByContent(String content);
}
