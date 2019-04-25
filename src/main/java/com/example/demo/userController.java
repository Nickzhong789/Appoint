package com.example.demo;


import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class userController {

    @Autowired
    private userReposity userRepo;

    @GetMapping(value = "/user")
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @GetMapping(value = "/getUserNum")
    public JSONObject getByUserNum() {
        JSONObject resJson = new JSONObject();
        Integer userNum = userRepo.getUserNum();
        resJson.put("uNum", userNum);
        return resJson;
    }

    @GetMapping(value = "/getWeather/{city}")
    private String getWeatherbiCity(@PathVariable("city") String city) throws Exception{

        SslUtils sslUtils = new SslUtils();
        city = URLEncoder.encode(city, "utf-8");

        String apiUrl = String.format("https://www.sojson.com/open/api/weather/json.shtml?city=%s",city);

        URL url= new URL(apiUrl);
        if("https".equalsIgnoreCase(url.getProtocol())){
            sslUtils.ignoreSsl();
        }

        URLConnection open = url.openConnection();
        InputStream input = open.getInputStream();

        return IOUtils.toString(input,"utf-8");
    }

    @PostMapping(value = "/userLogin")
    public JSONObject getUserByName(@RequestBody reqLoginData reqLoginData) {
        String name = reqLoginData.getName();
        String pass = reqLoginData.getPassword();

        JSONObject resJson = new JSONObject();

        List<User> userList = userRepo.getByUserName(name);
        if (userList.size() == 0) {
            resJson.put("status", false);
            resJson.put("msg", "用户不存在！");
        }
        else {
            System.out.println(userList.get(0));
            if (!pass.equals(userList.get(0).getPassword()) ) {
                resJson.put("status", false);
                resJson.put("msg", "账户或密码错误！");
            }
            else {
                resJson.put("status", true);
                resJson.put("msg", "登陆成功！");
                resJson.put("name", userList.get(0).getUserName());
                resJson.put("password", userList.get(0).getPassword());
            }

        }
        return resJson;
    }

    @PostMapping(value = "/modifyUserPass")
    public JSONObject modifyUserPass(@RequestBody reqModifyPassData reqModifyPassData) {
        String userName = reqModifyPassData.getUserName();
        String pre_pass = reqModifyPassData.getPre_pass();
        String new_pass = reqModifyPassData.getNew_pass();

        JSONObject resJson = new JSONObject();

        List<User> userList = userRepo.getByUserName(userName);
        if (!pre_pass.equals(userList.get(0).getPassword())) {
            resJson.put("status", false);
            resJson.put("msg", "当前密码不正确");
        }
        else {
            userRepo.updatePassByName(userName, new_pass);
            resJson.put("status", true);
            resJson.put("msg", "修改成功，3秒后即将重新登录");
        }
        return resJson;
    }

    @PostMapping(value = "/signUpUser")
    public JSONObject addUser(@RequestBody reqSignUpData reqSignUpData) {
        String name = reqSignUpData.getUserName();
        String cname = reqSignUpData.getCerName();
        String pass = reqSignUpData.getPassword();
        String id_num = reqSignUpData.getIdNum();
        String phone = reqSignUpData.getPhone();

        JSONObject resJson = new JSONObject();
        List<User> userList = userRepo.getByUserName(name);

        if (userList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "该用户名已存在!");
            return resJson;
        }

        userList = userRepo.getByUserId(id_num);
        if (userList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "身份证号已存在!");
            return resJson;
        }

        User user = new User();
        user.setUserName(name);
        user.setCerName(cname);
        user.setPassword(pass);
        user.setIdNum(id_num);
        user.setPhone(phone);
        userRepo.save(user);



        resJson.put("status", true);
        resJson.put("msg", "注册成功!");
        return resJson;
    }

    @PostMapping(value = "/addUser")
    public User addUser(@RequestBody reqAddUserData reqAddUserData) {
        String u_name = reqAddUserData.getuName();
        String c_name = reqAddUserData.getcName();
        String u_pass = reqAddUserData.getPass();
        String id_num = reqAddUserData.getIdNum();
        String phone = reqAddUserData.getPhone();

        User user = new User();
        user.setUserName(u_name);
        user.setCerName(c_name);
        user.setPassword(u_pass);
        user.setIdNum(id_num);
        user.setPhone(phone);
        return userRepo.save(user);
    }

    @PostMapping(value = "/delUser/{userName}")
    public String delUser(@PathVariable("userName") String userName) {
        userRepo.delUserByName(userName);
        return "hhh";
    }

    @GetMapping(value = "/getUserByUname")
    public JSONObject getByUser(@RequestParam("uName") String uName) {
        List<User> userList = userRepo.getByUserName(uName);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(userList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getUserByCname")
    public JSONObject getUserByCname(@RequestParam("contact") String cName) {
        List<User> userList = userRepo.getUserByContact(cName);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(userList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getUserByIdNum")
    public JSONObject getUserByIdNum(@RequestParam("idNum") String idNum) {
        List<User> userList = userRepo.getUserByIdNum(idNum);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(userList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getUserByPhone")
    public JSONObject getUserByPhone(@RequestParam("phone") String phone) {
        List<User> userList = userRepo.getUserByPhone(phone);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(userList, resJson, index);

        return resJson;
    }

    private void makeResJson(List<User> userList, JSONObject resJson, Integer index) {
        if (userList.size() != 0) {
            for (User user : userList) {
                JSONObject data_list = new JSONObject();
                data_list.put("userName", user.getUserName());
                data_list.put("conName", user.getCerName());
                data_list.put("idNum", user.getIdNum());
                data_list.put("phone", user.getPhone());
                resJson.put("data" + index.toString(), data_list);
                index++;
            }
        }
    }
}
