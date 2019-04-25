package com.example.demo;


import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

@RestController
public class appointController {
    @Autowired
    private appointReposity appointRepo;

    @GetMapping(value = "/getAppointMsgToday")
    public JSONObject getAppointMsg(@RequestParam("date") String date) {
        JSONObject resJson = new JSONObject();
        System.out.println(date);

        Integer appoint_num = appointRepo.getUnderAppointNum(date);
        Integer drive_num = appointRepo.getDriveNum(date);

        resJson.put("p_num", appoint_num);
        resJson.put("car_num", drive_num);

        return resJson;
    }

    @PostMapping(value = "/getAppointMsgByDate")
    public JSONObject getAppointMsgBydate(@RequestBody reqAppointNumData reqAppointNumData) {
        JSONObject resJson = new JSONObject();


        String date1 = reqAppointNumData.getdate1();
        String date2 = reqAppointNumData.getdate2();
        String date3 = reqAppointNumData.getdate3();
        String date0 = reqAppointNumData.getdate0();

        List<String> dateList = Arrays.asList(date0, date1,date2,date3);

        for (String date : dateList) {
            JSONObject numJson = new JSONObject();
            Integer appoint_num = appointRepo.getUnderAppointNumStatics(date);
            Integer drive_num = appointRepo.getDriveNumStatics(date);
            numJson.put("aNum", appoint_num);
            numJson.put("dNum", drive_num);
            resJson.put(date, numJson);
        }

        return resJson;
    }

    @PostMapping(value = "/appoint")
    public JSONObject appoint(@RequestBody reqAppointData reqAppointData) {
        JSONObject resJson = new JSONObject();

        String date = reqAppointData.getDate();
        JSONObject jsonData = reqAppointData.getDatas();

        resJson.put("date", date);
        resJson.put("datas", jsonData);
        resJson.put("status", true);

        Set<String> keySet = jsonData.keySet();
        for (String key : keySet) {
            LinkedHashMap data = (LinkedHashMap) jsonData.get(key);

            Appointment appointment = new Appointment();
            appointment.setaTime(date);
            appointment.setConName(data.get("cerName").toString());
            appointment.setIdNum(data.get("idNum").toString());
            appointment.setPhone(data.get("phone").toString());
            appointment.setIsDrive(reqAppointData.getIs_drive());
            appointment.setCarNum(reqAppointData.getCarNum());
            appointment.setUserName(reqAppointData.getUserName());
            appointment.setStatus(reqAppointData.getStatus());
            appointRepo.save(appointment);
            //resJson.put("str", data.get("name").toString());
        }

        return resJson;
    }

    @GetMapping(value = "/getAppointRecord")
    public JSONObject getAppointRecord(@RequestParam("userName") String userName) {
        List<Appointment> appointList = appointRepo.getAppointByUser(userName);
        JSONObject resJson = new JSONObject();
        JSONObject appointHistoryJson = new JSONObject();
        JSONObject underAppointJson = new JSONObject();

        for (Appointment appoint : appointList) {

            Integer status = appoint.getStatus();
            if (status == 0) {
                String preDate = appoint.getaTime();
                Boolean preTag = true;
                Set<String> previousKeys = underAppointJson.keySet();
                for (String key : previousKeys) {
                    if (preDate.equals(key)) {
                        String preStr = underAppointJson.get(key).toString();
                        preStr += "," + appoint.getConName();
                        underAppointJson.put(key, preStr);
                        preTag = false;
                        break;
                    }
                }
                if (preTag) underAppointJson.put(preDate, appoint.getConName());
            } else {
                String date = appoint.getaTime();
                Boolean tag = true;
                Set<String> historyKeys = appointHistoryJson.keySet();
                for (String key : historyKeys) {
                    if (date.equals(key)) {
                        String str = appointHistoryJson.get(key).toString();
                        str += "," + appoint.getConName();
                        appointHistoryJson.put(key, str);
                        tag = false;
                        break;
                    }
                }
                if (tag) {
                    appointHistoryJson.put(date, appoint.getConName());
                }
            }
        }

        if (appointHistoryJson.size() != 0) resJson.put("history", appointHistoryJson);
        if (underAppointJson.size() != 0) resJson.put("underAppoint", underAppointJson);

        return resJson;
    }

    @GetMapping(value = "/getUnderAppoint")
    public JSONObject getUnderAppoint(@RequestParam("userName") String userName, @RequestParam("date") String date) {
        List<Appointment> appointList = appointRepo.getUnderAppointByUserAndDate(userName, date);
        JSONObject resJson = new JSONObject();
        if (appointList.size() != 0) {
            Integer idx = 0;
            for (Appointment appoint : appointList) {
                JSONObject data = new JSONObject();
                data.put("contact", appoint.getConName());
                data.put("id_num", appoint.getIdNum());
                data.put("phone", appoint.getPhone());
                data.put("date", appoint.getaTime());
                resJson.put("data" + idx.toString(), data);
                idx++;
            }
        }
        else {
            resJson.put("status", false);
        }
        return resJson;
    }

    @PostMapping(value = "/delAppointByUser")
    public String delAppoint(@RequestParam("userName") String userName) {
        appointRepo.delAppointByUser(userName);
        return "Success!";
    }

    @GetMapping(value = "/delAppointOfContact")
    public ModelAndView delAppointOfContact(@RequestParam("userName") String userName,
                                            @RequestParam("contact") String contact,
                                            @RequestParam("date") String date) {
        appointRepo.delAppointContact(userName, contact, date);
        return new ModelAndView("/user_personalcenter_myreservation.html?userName=" + userName);
    }

    @GetMapping(value = "/delAppointByDate")
    public ModelAndView delAppointOfDate(@RequestParam("userName") String userName, @RequestParam("date") String date) {
        appointRepo.delAppointByDate(userName, date);
        return new ModelAndView("/user_personalcenter_myreservation.html?userName=" + userName);
    }

    @GetMapping(value = "/getAppointByContact")
    public JSONObject getByContact(@RequestParam("contact") String conName) {
        List<Appointment> appointList = appointRepo.getAppointByContact(conName);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getAppointByUname")
    public JSONObject getByUser(@RequestParam("uName") String uName) {
        List<Appointment> appointList = appointRepo.getAppointByUser(uName);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getAppointByIdNum")
    public JSONObject getByIdNum(@RequestParam("idNum") String idNum) {
        List<Appointment> appointList = appointRepo.getAppointById(idNum);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getAppointByPhone")
    public JSONObject getByPhone(@RequestParam("phone") String phone) {
        List<Appointment> appointList = appointRepo.getAppointByPhone(phone);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getAppointByCar")
    public JSONObject getByCarNum(@RequestParam("carNum") String carNum) {
        List<Appointment> appointList = appointRepo.getAppointByCar(carNum);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getAppointByDate")
    public JSONObject getByDate(@RequestParam("date") String date) {
        List<Appointment> appointList = appointRepo.getAppointByDate(date);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(appointList, resJson, index);

        return resJson;
    }

    private void makeResJson(List<Appointment> appointList, JSONObject resJson, Integer index) {
        if (appointList.size() != 0) {
            for (Appointment appoint : appointList) {
                JSONObject data_list = new JSONObject();
                data_list.put("userName", appoint.getUserName());
                data_list.put("conName", appoint.getConName());
                data_list.put("idNum", appoint.getIdNum());
                data_list.put("phone", appoint.getPhone());
                data_list.put("carNum", appoint.getCarNum());
                resJson.put("data" + index.toString(), data_list);
                index++;
            }
        }
    }
}
