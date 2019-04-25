package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Certification {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "c_name")
    private String cerName;

    @NotNull
    @Column(name = "id_num", length = 18)
    private String idNum;

    @NotNull
    @Column(name = "phone", length = 11)
    private String phone;

    public Certification() {
    }

    public Integer getId() {
        return id;
    }

    public String getCerName() {
        return cerName;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCerName(String cerName) {
        this.cerName = cerName;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
