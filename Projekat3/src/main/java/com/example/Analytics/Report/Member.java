package com.example.Analytics.Report;

import java.io.Serializable;

public class Member implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private Boolean active;

    public Member() {
    }

    public Member(Integer id, String firstName, String lastName, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public Integer getId() {        return id;    }

    public void setId(Integer id) {        this.id = id;    }

    public String getFirstName() {        return firstName;    }

    public void setFirstName(String firstName) {        this.firstName = firstName;    }

    public String getLastName() {        return lastName;    }

    public void setLastName(String lastName) {        this.lastName = lastName;    }

    public Boolean getActive() {        return active;    }

    public void setActive(Boolean active) {        this.active = active;    }
}
