package com.fadgiras.searchengine.model;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name = "Index")
public class Index {

    @Id
    private String word;

    @ElementCollection
    private ArrayList<Integer> documentIds;

    public Index() {
    }

    public Index(String word, ArrayList<Integer> documentIds) {
        this.word = word;
        this.documentIds = documentIds;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Integer> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(ArrayList<Integer> documentIds) {
        this.documentIds = documentIds;
    }

    public void addDocumentId(int documentId) {
        documentIds.add(documentId);
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
