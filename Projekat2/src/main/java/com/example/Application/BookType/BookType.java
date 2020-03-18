package com.example.Application.BookType;

import javax.persistence.*;

@Entity
@Table(name = "book_type")
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @Column(name = "latency_fee")
    private Double latencyFee;

    @Column(name = "library_read_only")
    private Boolean libraryReadOnly;

    public BookType() {
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
