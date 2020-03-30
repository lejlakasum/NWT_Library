package com.example.Reservation.Member;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "member_id")
public class Member extends RepresentationModel<Member> {
    @Id
    @NotNull(message = "Variable id must not be null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "join_date")
    @NotNull(message = "Variable join date must not be null")
    private Date joinDate;

    @Column(name = "active")
    @NotNull(message = "Variable active must not be null")
    private Boolean active;

    @Column(name = "first_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable first name must not be null")
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable last name must not be null")
    private String lastName;

    @Column(name = "birth_date")
    @NotNull(message = "Variable birth date must not be null")
    private Date birthDate;

    @Column(name = "membership_type")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable membership type name must not be null")
    private String membershipTypeName;

    @Column(name = "role_name")
    @Size(min = 2, max = 25, message = "Size must be between 2 and 25.")
    @NotNull(message = "Variable role name must not be null")
    private String roleName;

    public Member(){}

    public Member(@NotNull(message = "Variable id must not be null") Integer id, @NotNull(message = "Variable join date must not be null") Date joinDate, @NotNull(message = "Variable active must not be null") Boolean active, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable first name must not be null") String firstName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable last name must not be null") String lastName, @NotNull(message = "Variable birth date must not be null") Date birthDate, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable membership type name must not be null") String membershipTypeName, @Size(min = 2, max = 25, message = "Size must be between 2 and 25.") @NotNull(message = "Variable role name must not be null") String roleName) {
        this.id = id;
        this.joinDate = joinDate;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.membershipTypeName = membershipTypeName;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public String getMembershipTypeName() {
        return membershipTypeName;
    }

    public void setMembershipTypeName(String membershipTypeName) {
        this.membershipTypeName = membershipTypeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
