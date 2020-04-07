package com.example.Analytics.Report;

import java.io.Serializable;

public class Impression implements Serializable {

    String comment;
    Integer rating;
    Member member;

    public Impression() {
    }

    public Impression(String comment, Integer rating, Member member) {
        this.comment = comment;
        this.rating = rating;
        this.member = member;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
