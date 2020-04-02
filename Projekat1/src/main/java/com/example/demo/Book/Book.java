package com.example.demo.Book;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book extends RepresentationModel<Book> {

    @Id
    @NotNull
    private Integer id;

    @NotNull
    private String isbn;

    public Book() {
    }

    public Book(String isbn){
        this.isbn=isbn;
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
