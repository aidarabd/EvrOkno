package com.example.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="orders")
public class order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;



    @Column(name="user_name")
    private String userFullName;

    @Column(name="phone_num")
    private int phoneNum;

    @Column(name="horizontal")
    private int horizontal;

    @Column(name="vertical")
    private int vertical;

    @Column(name="podokonnik")
    private String podokonnik;

    @Column(name = "additional")
    private String additional;

    @Column(name="prof_type")
    private String profType;

    @Column(name="color")
    private String color;


    @Column(name="quantity")
    private int quantity;



    @Column(name="date")
    @Temporal(TemporalType.DATE)
    private Date date;


    public order(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public String getPodokonnik() {
        return podokonnik;
    }

    public void setPodokonnik(String podokonnik) {
        this.podokonnik = podokonnik;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getProfType() {
        return profType;
    }

    public void setProfType(String profType) {
        this.profType = profType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

}
