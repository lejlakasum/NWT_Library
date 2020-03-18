package com.example.Application.Impression;

import com.example.Application.Book.Book;
import com.example.Application.Member.Member;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book_member_impression")
public class Impression {

    private static final String RATING_ERROR_MSG = "Rating must be between 1 and 5";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

}
