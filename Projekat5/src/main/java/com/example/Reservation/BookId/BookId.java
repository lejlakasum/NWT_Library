package com.example.Reservation.BookId;

import javax.persistence.*;

@Entity
@Table(name = "book_id")
public class BookId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public BookId() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
