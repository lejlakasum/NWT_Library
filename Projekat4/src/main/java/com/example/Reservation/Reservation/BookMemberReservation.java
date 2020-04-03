package com.example.Reservation.Reservation;

import com.example.Reservation.Member.Member;
import com.example.Reservation.Book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "book_member_reservation")
public class BookMemberReservation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Variable member must not be null")
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull(message = "Variable book must not be null")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull(message = "Variable date od reservation must not be null")
    @Column(name = "date_time_of_reservation")
    private Date dateTimeOfReservation;

    public BookMemberReservation() { }

    public BookMemberReservation(Integer id, @NotNull(message = "Variable member must not be null") Member member, @NotNull(message = "Variable book must not be null") Book book, @NotNull(message = "Variable date od reservation must not be null") Date dateTimeOfReservation) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.dateTimeOfReservation = dateTimeOfReservation;
    }

    public BookMemberReservation(@NotNull(message = "Variable member must not be null") Member member, @NotNull(message = "Variable book must not be null") Book book, @NotNull(message = "Variable date od reservation must not be null") Date dateTimeOfReservation) {
        this.member = member;
        this.book = book;
        this.dateTimeOfReservation = dateTimeOfReservation;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {  return member;  }
    public void setMember(Member member) {    this.member = member;  }

    public Book getBook() {   return book;  }
    public void setBook(Book book) {    this.book = book;   }

    public Date getDateTimeOfReservation() {
        return dateTimeOfReservation;
    }
    public void setDateTimeOfReservation(Date dateTimeOfReservation) {this.dateTimeOfReservation = dateTimeOfReservation;  }
}
