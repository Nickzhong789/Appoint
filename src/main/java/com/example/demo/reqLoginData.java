package com.example.demo;

public class reqLoginData {
    private String name;  // 一定要与前端data的key一致，否则获取不到数据
    private String password;

    public reqLoginData() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
