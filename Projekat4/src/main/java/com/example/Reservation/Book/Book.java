package com.example.Reservation.Book;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book extends RepresentationModel<Book> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ISBN")
    @NotNull(message = "Variable isbn must not be null")
    private String isbn;

    @Column (name = "date_publisher")
    @NotNull(message = "Variable date publisher must not be null")
    private Date datePublisher;

    @Column (name = "available")
    @NotNull(message = "Variable available must not be null")
    private Boolean available;

    @Column (name = "book_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable book name must not be null")
    private String bookName;

    @Column (name = "genre_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable genre name must not be null")
    private String genreName;

    @Column (name = "publisher_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable publisher name must not be null")
    private String publisherName;

    @Column (name = "library_read_only")
    @NotNull(message = "Variable library read only must not be null")
    private Boolean libraryReadOnly;

    public Book() {}

    public Book(Integer id, @NotNull(message = "Variable isbn must not be null") String isbn, @NotNull(message = "Variable date publisher must not be null") Date datePublisher, @NotNull(message = "Variable available must not be null") Boolean available, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable book name must not be null") String bookName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable genre name must not be null") String genreName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable publisher name must not be null") String publisherName, @NotNull(message = "Variable library read only must not be null") Boolean libraryReadOnly) {
        this.id = id;
        this.isbn = isbn;
        this.datePublisher = datePublisher;
        this.available = available;
        this.bookName = bookName;
        this.genreName = genreName;
        this.publisherName = publisherName;
        this.libraryReadOnly = libraryReadOnly;
    }

    public Book(@NotNull(message = "Variable isbn must not be null") String isbn, @NotNull(message = "Variable date publisher must not be null") Date datePublisher, @NotNull(message = "Variable available must not be null") Boolean available, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable book name must not be null") String bookName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable genre name must not be null") String genreName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable publisher name must not be null") String publisherName, @NotNull(message = "Variable library read only must not be null") Boolean libraryReadOnly) {
        this.isbn = isbn;
        this.datePublisher = datePublisher;
        this.available = available;
        this.bookName = bookName;
        this.genreName = genreName;
        this.publisherName = publisherName;
        this.libraryReadOnly = libraryReadOnly;
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

    public Date getDatePublisher() {
        return datePublisher;
    }

    public void setDatePublisher(Date datePublisher) {
        this.datePublisher = datePublisher;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Boolean getLibraryReadOnly() {
        return libraryReadOnly;
    }

    public void setLibraryReadOnly(Boolean libraryReadOnly) {
        this.libraryReadOnly = libraryReadOnly;
    }
}
