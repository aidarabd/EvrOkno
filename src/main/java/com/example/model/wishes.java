package com.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
    @Table(name = "contacts")
    public class wishes implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "submission_id")
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name = "user_full_name")
    private String userFullName;



    @Column(name="name")
    private String name;

    @Column(name="phone_num")
    private String phoneNum;


    @Column(name="comment")
    private String comment;


    @Column(name="date")
    @Temporal(TemporalType.DATE)
    private Date date;

    public Date getDate() {
        return date;
    }



    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }
    public wishes(){

    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
