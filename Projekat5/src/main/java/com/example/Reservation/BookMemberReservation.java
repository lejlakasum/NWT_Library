package com.example.Reservation;

import com.example.MemberId.MemberId;
import com.example.BookId.BookId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation_book_member")
public class BookMemberReservation {
    @Id
    @GeneratedValue
    private int id;

    @JoinTable(name = "member_id")
    private MemberId memberId;

    @JoinTable(name = "book_id")
    private BookId bookId;

    @Column(name = "date_time_of_reservation")
    private Date dateTimeOfReservation;

    public BookMemberReservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public com.example.MemberId.MemberId getMemberId() {
        return memberId;
    }

    public void setMemberId(com.example.MemberId.MemberId memberId) {
        this.memberId = memberId;
    }

    public BookId getBookId() {
        return bookId;
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public Date getDateTimeOfReservation() {
        return dateTimeOfReservation;
    }

    public void setDateTimeOfReservation(Date dateTimeOfReservation) {
        this.dateTimeOfReservation = dateTimeOfReservation;
    }
}
