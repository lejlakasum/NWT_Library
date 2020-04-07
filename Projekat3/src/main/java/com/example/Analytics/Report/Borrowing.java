package com.example.Analytics.Report;

import java.io.Serializable;
import java.util.Date;

public class Borrowing implements Serializable {

    private Integer id;
    private Book book;
    private Member member;
    private Date borrowingDate;
    private Boolean returned;

    public Borrowing() {    }

    public Borrowing(Integer id, Book book, Member member, Date borrowingDate, Boolean returned) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.borrowingDate = borrowingDate;
        this.returned = returned;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }
}
