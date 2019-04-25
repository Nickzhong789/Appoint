package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Schoolfellow {
    @Id
    @GeneratedValue
    @NotNull
    private Integer id;

    @NotNull
    @Column(name = "s_name")
    private String sName;

    @NotNull
    @Column(name = "s_num")
    private String sNum;

    @NotNull
    @Column(name = "id_num")
    private String idNum;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "s_pic")
    private String sPic;

    @NotNull
    @Column(name = "is_pass")
    private Integer isPass;

    @NotNull
    @Column(name = "u_name")
    private String userName;

    public Schoolfellow() {}

    public Integer getId() {
        return id;
    }

    public String getsName() {
        return sName;
    }

    public String getsNum() {
        return sNum;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getPhone() {
        return phone;
    }

    public String getsPic() {
        return sPic;
    }

    public Integer getIsPass() {
        return isPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsNum(String sNum) {
        this.sNum = sNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setsPic(String sPic) {
        this.sPic = sPic;
    }

    public void setIsPass(Integer isPass) {
        this.isPass = isPass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
