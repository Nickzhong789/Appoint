package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Appointment {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "c_name")
    private String conName;

    @NotNull
    @Column(name = "a_time")
    private String aTime;

    @NotNull
    @Column(name = "phone", length = 11)
    private String phone;

    @NotNull
    @Column(name = "id_num", length = 18)
    private String idNum;

    @NotNull
    @Column(name = "is_drive")
    private Integer isDrive;

    @Column(name = "car_num")
    private String carNum;

    @NotNull
    @Column(name = "u_name")
    private String userName;

    @NotNull
    @Column(name = "status")
    private Integer status;

    public Appointment() {

    }

    public Integer getId() {
        return id;
    }

    public String getaTime() {
        return aTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getCarNum() {
        return carNum;
    }

    public Integer getIsDrive() {
        return isDrive;
    }

    public String getUserName() {
        return userName;
    }

    public String getConName() {
        return conName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setaTime(String aTime) {
        this.aTime = aTime;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public void setIsDrive(Integer isDrive) {
        this.isDrive = isDrive;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
