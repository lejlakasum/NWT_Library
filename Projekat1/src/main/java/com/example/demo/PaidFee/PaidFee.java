package com.example.demo.PaidFee;

import com.example.demo.Fee.Fee;
import com.example.demo.Member.Member;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Date;

@Entity
@Table(name = "paid_fee")
public class PaidFee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "fee_id")
    private Fee fee;

    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    public PaidFee() {
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
