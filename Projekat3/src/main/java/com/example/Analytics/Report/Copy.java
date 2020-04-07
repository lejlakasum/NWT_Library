package com.example.Analytics.Report;

import java.io.Serializable;

public class Copy implements Serializable {

    private Integer id;
    private String bookName;

    public Copy() {    }

    public Copy(Integer id, String bookName) {
        this.id = id;
        this.bookName = bookName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
