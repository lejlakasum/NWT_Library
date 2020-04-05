package com.example.Analytics.Report;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class Book  implements Serializable {

    private Integer id;
    private String isbn;
    private Copy copy;
    private BookType bookType;
    private Genre genre;
    private Publisher publisher;
    private Date publishedDate;
    private Boolean available;

    public Book() {
    }

    public Book(Integer id, String isbn, Copy copy, BookType bookType, Genre genre, Publisher publisher, Date publishedDate, Boolean available) {
        this.id = id;
        this.isbn = isbn;
        this.copy = copy;
        this.bookType = bookType;
        this.genre = genre;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.available = available;
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

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
