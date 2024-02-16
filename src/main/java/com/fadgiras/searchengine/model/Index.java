package com.fadgiras.searchengine.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "Index")
public class Index {

    @Id
    private String word;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "index_book",
            joinColumns = { @JoinColumn(name = "word")},
            inverseJoinColumns = { @JoinColumn(name = "id")})
    private Set<Book> documentIds;

    public Index() {
    }

    public Index(String word, Set<Book> documentIds) {
        this.word = word;
        this.documentIds = documentIds;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Book> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(Set<Book> documentIds) {
        this.documentIds = documentIds;
    }

    public void addDocumentId(Book document) {
        documentIds.add(document);
    }

    public void removeDocumentId(int documentId) {
        documentIds.remove(documentId);
    }

    public boolean containsDocumentId(int documentId) {
        return documentIds.contains(documentId);
    }

    public int getDocumentCount() {
        return documentIds.size();
    }

    public String toString() {
        return "Index{word='" + word + "', documentIds=" + documentIds + "}";
    }
}
