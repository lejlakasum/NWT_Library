package com.example.Application.Copy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "copy")
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "book_name")
    @NotNull
    private String bookName;

    public Copy() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
