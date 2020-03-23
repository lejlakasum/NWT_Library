package com.example.Reservation.MemberId;

import javax.persistence.*;

@Entity
@Table(name = "member_id")
public class MemberId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public MemberId(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
