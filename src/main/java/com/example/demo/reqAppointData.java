package com.example.demo;

import net.minidev.json.JSONObject;

public class reqAppointData {
    private String date;

    private JSONObject datas;

    private String userName;

    private Integer is_drive;

    private String carNum;

    private Integer status;

    public reqAppointData() {
    }

    public String getDate() {
        return date;
    }

    public JSONObject getDatas() {
        return datas;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getIs_drive() {
        return is_drive;
    }

    public String getCarNum() {
        return carNum;
    }

    public Integer getStatus() {
        return status;
    }
}
