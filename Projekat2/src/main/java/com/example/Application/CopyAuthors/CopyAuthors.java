package com.example.Application.CopyAuthors;

import com.example.Application.Author.Author;
import com.example.Application.Copy.Copy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "copy_author")
public class CopyAuthors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Variable copy must not be null.")
    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @NotNull(message = "Variable author must not be null.")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public CopyAuthors() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Copy getCopy() {
        return copy;
    }

    public void setCopy(Copy copy) {
        this.copy = copy;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
