package com.example.demo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Admin {

    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "a_name")
    private String adName;

    @NotNull
    @Column(name = "a_pass")
    private String password;

    public Admin() {
    }

    public Integer getId() {
        return id;
    }

    public String getAdName() {
        return adName;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.adName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
