package com.example.demo;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class adminController {

    @Autowired
    private adminReposity adminRepo;

    @PostMapping(value = "/adminLogin")
    public JSONObject getAdminByName(@RequestBody reqLoginData reqLoginData) {
        String name = reqLoginData.getName();
        String pass = reqLoginData.getPassword();

        JSONObject resJson = new JSONObject();

        List<Admin> adminList = adminRepo.getByAdminName(name);
        if (0 == adminList.size()) {
            resJson.put("status", false);
            resJson.put("msg", "管理员不存在！");
        }
        else {
            if (!pass.equals(adminList.get(0).getPassword()) ) {
                resJson.put("status", false);
                resJson.put("msg", "账户或密码错误！");
            }
            else {
                resJson.put("status", true);
                resJson.put("msg", "登陆成功！");
                resJson.put("name", adminList.get(0).getAdName());
                resJson.put("password", adminList.get(0).getPassword());
            }

        }
        return resJson;
    }

}
