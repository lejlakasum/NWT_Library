package com.example.Analytics.Report;

import java.util.Date;

public class BookNEW {

    private Integer id;
    private String isbn;
    private String bookTypeName;
    private Boolean libraryReadOnly;
    private String genreName;
    private String publisherName;
    private Date publishedDate;
    private Boolean available;
    private Integer rating;
    private Member member;

    public BookNEW() {}

    public BookNEW(Integer id, String isbn, String bookTypeName, Boolean libraryReadOnly, String genreName, String publisherName, Date publishedDate, Boolean available, Integer rating, Member member) {
        this.id = id;
        this.isbn = isbn;
        this.bookTypeName = bookTypeName;
        this.libraryReadOnly = libraryReadOnly;
        this.genreName = genreName;
        this.publisherName = publisherName;
        this.publishedDate = publishedDate;
        this.available = available;
        this.rating = rating;
        this.member = member;
    }

    public Integer getId() {return id;  }

    public void setId(Integer id) {   this.id = id;   }

    public String getIsbn() {    return isbn;   }

    public void setIsbn(String isbn) {    this.isbn = isbn;   }

    public String getBookTypeName() {    return bookTypeName;    }

    public void setBookTypeName(String bookTypeName) {    this.bookTypeName = bookTypeName;    }

    public Boolean getLibraryReadOnly() {    return libraryReadOnly;    }

    public void setLibraryReadOnly(Boolean libraryReadOnly) {    this.libraryReadOnly = libraryReadOnly;   }

    public String getGenreName() {   return genreName;  }

    public void setGenreName(String genreName) {  this.genreName = genreName;    }

    public String getPublisherName() {    return publisherName;  }

    public void setPublisherName(String publisherName) {    this.publisherName = publisherName;    }

    public Date getPublishedDate() {    return publishedDate;    }

    public void setPublishedDate(Date publishedDate) {   this.publishedDate = publishedDate;    }

    public Boolean getAvailable() {        return available;    }

    public void setAvailable(Boolean available) { this.available = available;    }

    public Integer getRating() {   return rating;   }

    public void setRating(Integer rating) {    this.rating = rating;   }

    public Member getMember() {    return member;    }

    public void setMember(Member member) {      this.member = member;    }
}
