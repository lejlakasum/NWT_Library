package com.example.demo.Member;

import com.example.demo.MembershipType.MembershipType;
import com.example.demo.Profile.Profile;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member")
public class Member extends RepresentationModel<Member> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "membership_type_id")
    private MembershipType membershipTypeId;

    @Column(name = "join_date")
    private Date joinDate;

    private Boolean active;

    public Member() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public MembershipType getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(MembershipType membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
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
}
