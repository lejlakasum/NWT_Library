package com.example.Application.Impression;

import com.example.Application.Book.Book;
import com.example.Application.Member.Member;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "impression")
public class Impression {

    private static final String RATING_ERROR_MSG = "Rating must be between 1 and 5";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Min(value = 1, message = RATING_ERROR_MSG)
    @Max(value = 5, message = RATING_ERROR_MSG)
    @NotNull
    @Column(name = "rate")
    private Integer rating;

    @NotNull
    @Column(name = "impression")
    private String comment;

    public Impression(Book book, Member member, @Min(value = 1, message = RATING_ERROR_MSG) @Max(value = 5, message = RATING_ERROR_MSG) @NotNull Integer rating, @NotNull String comment) {
        this.book = book;
        this.member = member;
        this.rating = rating;
        this.comment = comment;
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
