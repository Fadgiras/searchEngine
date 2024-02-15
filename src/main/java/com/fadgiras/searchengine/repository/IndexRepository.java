package com.fadgiras.searchengine.repository;

import com.fadgiras.searchengine.model.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexRepository extends JpaRepository<Index, String> {

    //get all indexes
    @Query("SELECT i FROM Index i")
    List<Index> getAllIndexes();

    //get index by word
    @Query("SELECT i FROM Index i WHERE i.word = ?1")
    Index getIndexByWord(String word);

    //get index by document id
//    @Query("SELECT i FROM Index i WHERE ?1 IN i.documentIds")
//    Index getIndexByDocumentId(int documentId);

    //get all words
    @Query("SELECT i.word FROM Index i")
    List<String> getAllWords();

}
