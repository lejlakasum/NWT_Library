package com.example.demo.PaidFee;

import com.example.demo.Book.Book;
import com.example.demo.Fee.Fee;
import com.example.demo.Member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.util.Date;

@Entity
@Table(name = "paid_fee")
public class PaidFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    @Column(name = "payment_date")
    @NotNull
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    public PaidFee() {
    }

    public PaidFee(Member member,Fee fee,Date date, Book book){
        this.member=member;
        this.fee=fee;
        this.paymentDate=date;
        this.book=book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
