package com.example.Analytics.Report;

import java.io.Serializable;

public class BookType implements Serializable {

    private Integer id;
    private String name;
    private Double latencyFee;
    private Boolean libraryReadOnly;

    public BookType() {    }

    public BookType(Integer id, String name, Double latencyFee, Boolean libraryReadOnly) {
        this.id = id;
        this.name = name;
        this.latencyFee = latencyFee;
        this.libraryReadOnly = libraryReadOnly;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatencyFee() {
        return latencyFee;
    }

    public void setLatencyFee(Double latencyFee) {
        this.latencyFee = latencyFee;
    }

    public Boolean getLibraryReadOnly() {
        return libraryReadOnly;
    }

    public void setLibraryReadOnly(Boolean libraryReadOnly) {
        this.libraryReadOnly = libraryReadOnly;
    }
}
