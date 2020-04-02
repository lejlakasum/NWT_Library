package com.example.demo.Member;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MemberDTO implements Serializable {

    @Id
    @NotNull(message = "Variable id must not be null.")
    private Integer id;

    @Size(min = 2, max = 25, message = "FirstName must be between 2 and 25.")
    @NotNull(message = "Variable firstName must not be null.")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 30, message = "LastName must be between 2 and 25.")
    @NotNull(message = "Variable lastName must not be null.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Variable active must not be null.")
    private Boolean active;

    public MemberDTO() {
    }

    public MemberDTO(Integer id, String firstName, String lastName, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
