package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "u_name")
    private String userName;

    @NotNull
    @Column(name = "c_name")
    private String cerName;

    @NotNull
    @Column(name = "u_pass")
    private String password;

    @NotNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NotNull
    @Column(name = "id_num", length = 18)
    private String idNum;



    public User () {

    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getCerName() {
        return cerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdNum(String id_num) {
        this.idNum = id_num;
    }
}
