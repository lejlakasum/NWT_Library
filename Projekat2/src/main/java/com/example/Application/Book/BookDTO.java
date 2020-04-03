package com.example.Application.Book;

import java.io.Serializable;

public class BookDTO implements Serializable {

    private Integer id;

    private String isbn;

    public BookDTO() {
    }

    public BookDTO(Integer id, String isbn) {
        this.id = id;
        this.isbn = isbn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
