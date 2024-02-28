package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Book;
import com.fadgiras.searchengine.model.JaccardBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JaccardBookRepository extends JpaRepository<JaccardBook, Book> {

    // get suggested books
    @Query(nativeQuery = true, value = "SELECT book_2_id AS suggestedBookId, jaccard_distance " +
            "FROM jaccard_book " +
            "WHERE book_1_id = ?1 " +
            "UNION " +
            "SELECT book_1_id, jaccard_distance " +
            "FROM jaccard_book " +
            "WHERE book_2_id = ?1 " +
            "ORDER BY jaccard_distance ASC")
    List<Object[]> getSuggestedBookIdsAndDistances(Long bookId);

}