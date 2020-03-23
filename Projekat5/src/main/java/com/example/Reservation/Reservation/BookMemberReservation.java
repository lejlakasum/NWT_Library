package com.example.Reservation.Reservation;

import com.example.Reservation.MemberId.MemberId;
import com.example.Reservation.BookId.BookId;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation_book_member")
public class BookMemberReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinTable(name = "member_id")
    private MemberId memberId;

    @JoinTable(name = "book_id")
    private BookId bookId;

    @Column(name = "date_time_of_reservation")
    private Date dateTimeOfReservation;

    public BookMemberReservation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public void setMemberId(MemberId memberId) {
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
