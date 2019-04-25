package com.example.demo;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class certiController {
    @Autowired
    private certiReposity certiRepo;

    @Autowired
    private contactReposity contactRepo;

    @GetMapping(value = "/getCertification")
    public JSONObject getCertiByUser(@RequestParam("userName") String userName) {
        List<Certification> certiList = certiRepo.getCertificationByUser(userName);
        JSONObject resJson = new JSONObject();
        if (certiList.size() != 0) {
            Integer idx = 0;
            for (Certification certification: certiList) {
                resJson.put(idx.toString(), certification);
                idx++;
            }
        }
        else {
            resJson.put("status", false);
        }
        return resJson;
    }

    @PostMapping(value = "/addCertification")
    public JSONObject addCertification(@RequestBody reqAddCertiData reqAddCertiData) {
        String name = reqAddCertiData.getName();
        String id_num = reqAddCertiData.getId();
        String phone = reqAddCertiData.getPhone();
        String userName = reqAddCertiData.getUserName();

        JSONObject resJson = new JSONObject();
        List<Certification> certiList;
        /*List<Certification> certiList = certiRepo.getCertificationByUser(userName);

        if (certiList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "该信息已存在!");
            return resJson;
        }*/

        List<Contacts> contactsList = contactRepo.getContactsByIdNum(id_num, userName);
        if (contactsList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "身份证号已存在!");
            return resJson;
        }

        certiList = certiRepo.getCertificationByCerName(name);
        if (certiList.size() != 0) {
            Contacts contacts = new Contacts();
            contacts.setIdNum(id_num);
            contacts.setUserName(userName);
            contactRepo.save(contacts);
        }
        else {
            Certification certification = new Certification();
            certification.setCerName(name);
            certification.setIdNum(id_num);
            certification.setPhone(phone);
            certiRepo.save(certification);

            Contacts contacts = new Contacts();
            contacts.setIdNum(id_num);
            contacts.setUserName(userName);
            contactRepo.save(contacts);
        }


        resJson.put("status", true);
        resJson.put("msg", "注册成功!");
        return resJson;
    }
}
