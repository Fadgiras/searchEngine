package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;

//UWU
import java.util.regex.*;
import org.springframework.data.repository.CrudRepository;

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

    //UWU get book by content containing
    @Query("SELECT b FROM Book b WHERE b.content LIKE %:term%")
    List<Book> findByContentContaining(@Param("term") String term);

    @Query(value="SELECT b.* FROM Book b WHERE b.content ~* :term", nativeQuery = true)
    // @Query(value="SELECT b.id FROM Book b WHERE b.content ~* :term", nativeQuery = true)
    // @Query(value="SELECT b.* FROM Book b WHERE (ANY(regexp_matches(b.content, :term, 'i')) IS NOT NULL)", nativeQuery = true)
    // @Query(value="SELECT b.* FROM Book b WHERE array_length(regexp_matches(b.content, :term, 'i'), 1) IS NOT NULL", nativeQuery = true)
    List<Book> findByContentRegex(@Param("term") String term);

    //Recherche de plusieurs mots (recherche OR) :
    @Query(value="SELECT b.* FROM Book b WHERE b.content ~* :term1 OR b.content ~* :term2", nativeQuery = true)
    List<Book> findByContentRegex(@Param("term1") String term1, @Param("term2") String term2);

    //Recherche de tous les mots (recherche AND) :
    @Query(value="SELECT b.* FROM Book b WHERE b.content ~* :term1 AND b.content ~* :term2", nativeQuery = true)
    List<Book> findByContentTwoTerms(@Param("term1") String term1, @Param("term2") String term2);

    //Recherche de mots exacts (pas une sous-chaîne d'un mot plus long) :
    @Query(value="SELECT b.* FROM Book b WHERE b.content ~* '\\m:term\\M'", nativeQuery = true)
    List<Book> findByExactWord(@Param("term") String term);

    //Recherche de mots avec un certain préfixe :
    @Query(value="SELECT b.* FROM Book b WHERE b.content ~* ':prefix\\w*'", nativeQuery = true)
    List<Book> findByContentPrefix(@Param("prefix") String prefix);

    //Recherche de mots en RegEx :
    @Query(value = "SELECT b.* FROM Book b WHERE b.title ~* :regex", nativeQuery = true)
    List<Book> findBooksByTitleWithRegex(@Param("regex") String regex);
}
