package com.example.MemberId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member_id")
public class MemberId {
    @Id
    @GeneratedValue
    private int id;
}
