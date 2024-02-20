package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.RIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RIndexRepository extends JpaRepository<RIndex, String> {

    //get index by word
    @Query("SELECT i FROM RIndex i WHERE i.word = ?1")
    RIndex getIndexByWord(String word);

    //get index by document id
//    @Query("SELECT i FROM Index i WHERE ?1 IN i.documentIds")
//    Index getIndexByDocumentId(int documentId);

    //get all words
    @Query("SELECT i.word FROM RIndex i")
    List<String> getAllWords();

    //get index by book id
//    @Query("SELECT i FROM RIndex i WHERE ?1 IN i.books")
//    RIndex getIndexByBookId(int bookId);

}
