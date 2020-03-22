package com.example.Application.Book;

import com.example.Application.BookType.BookType;
import com.example.Application.Copy.Copy;
import com.example.Application.Genre.Genre;
import com.example.Application.Publisher.Publisher;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Variable isbn must not be null")
    private String isbn;

    @NotNull(message = "Variable copy must not be null")
    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @NotNull(message = "Variable bookType must not be null")
    @ManyToOne
    @JoinColumn(name = "book_type_id")
    private BookType bookType;

    @NotNull(message = "Variable genre must not be null")
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotNull(message = "Variable publisher must not be null")
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "published_date")
    private Date publishedDate;

    @NotNull(message = "Variable available must not be null")
    private Boolean available;

    public Book() {
    }

    public Book(Integer id, @NotNull(message = "Variable isbn must not be null") String isbn, @NotNull(message = "Variable copy must not be null") Copy copy, @NotNull(message = "Variable bookType must not be null") BookType bookType, @NotNull(message = "Variable genre must not be null") Genre genre, @NotNull(message = "Variable publisher must not be null") Publisher publisher, Date publishedDate, @NotNull(message = "Variable available must not be null") Boolean available) {
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
