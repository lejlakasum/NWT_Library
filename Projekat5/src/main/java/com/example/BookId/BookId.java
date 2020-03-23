package com.example.BookId;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class BookId {
    @Id
    @GeneratedValue
    private int id;
}
