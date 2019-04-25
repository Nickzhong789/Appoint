package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Contacts {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "id_num", length = 18)
    private String idNum;

    @NotNull
    @Column(name = "u_name")
    private String userName;

    public Contacts() {
    }

    public Integer getId() {
        return id;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
