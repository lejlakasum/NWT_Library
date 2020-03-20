package com.example.Application.Author;

import com.example.Application.Country.Country;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Variable firstName must not be null")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Variable lastName must not be null")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Variable birthDate must not be null!")
    @Column(name = "birth_date")
    private Date birthDate;

    @NotNull(message = "Variable country must not be null")
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Author() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
