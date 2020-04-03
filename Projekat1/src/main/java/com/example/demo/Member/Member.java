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
    private MembershipType membershipType;

    @Column(name = "join_date")
    private Date joinDate;

    private Boolean active;

    public Member() {
    }

    public Member(Profile profile, MembershipType membershipType, Date date, Boolean active){
        this.profile=profile;
        this.membershipType=membershipType;
        this.joinDate=date;
        this.active=active;
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

    public MembershipType getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(MembershipType membershipType) {
        this.membershipType = membershipType;
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
