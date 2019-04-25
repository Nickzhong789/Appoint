package com.example.demo;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
public class sfController {

    private final ResourceLoader resourceLoader;

    @Autowired
    private sfReposity sfRepo;

    public sfController() {
        this.resourceLoader = null;
    }

    @Value("${web.upload-path}")
    private String path;

    @RequestMapping(value = "/toUpload")
    public String toUpload(){
        return "test";
    }

    @RequestMapping("/fileUpload")
    public ModelAndView upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){

        // 要上传的目标文件存放路径

        //String localPath = "E:/Develop/Files/Photos";
        String localPath = "/Users/user/Documents/IdeaProject/Appoint/src/main/resources/static/img_upload";


        // 上传成功或者失败的提示
        String msg;

        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";

        }

        // 显示图片
        map.put("msg", msg);
        map.put("fileName", file.getOriginalFilename());

        return new ModelAndView("/toUpload");
    }

    /**
     * 显示单张图片
     * @return
     */
    @RequestMapping("/show")
    public ResponseEntity showPhotos(String fileName){

        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = "/getSfOfNotPassed")
    public List<Schoolfellow> getSfNotPass() {
        return sfRepo.getSfOfNotPass();
    }

    @GetMapping(value = "/getSfOfPassed")
    public List<Schoolfellow> getSfPass() {
        return sfRepo.getSfOfPass();
    }

    @GetMapping(value = "/sfAuthen")
    public ModelAndView AuthenSf(@RequestParam("sNum") String sNum, @RequestParam("adName") String adName) {
        sfRepo.authenSfBySnum(sNum);
        return new ModelAndView("/manager_schoolfellow_A.html?adName=" + adName);
    }

    @PostMapping(value = "/addSchoolFellow")
    public JSONObject addSF(@RequestBody reqAddSfData reqAddSfData) {
        String name = reqAddSfData.getSfName();
        String s_num = reqAddSfData.getSfNum();
        String id_num = reqAddSfData.getIdNum();
        String phone = reqAddSfData.getPhone();
        String userName = reqAddSfData.getUserName();
        System.out.println(name);
        System.out.println(s_num);
        System.out.println(id_num);

        JSONObject resJson = new JSONObject();
        List<Schoolfellow> sfList = sfRepo.getSfBySfName(name);

        if (sfList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "该校友已存在!");
            return resJson;
        }

        sfList = sfRepo.getSfBySfNum(s_num);
        if (sfList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "学号已存在!");
            return resJson;
        }
        sfList = sfRepo.getSfBySfIdNum(id_num);
        if (sfList.size() != 0) {
            resJson.put("status", false);
            resJson.put("msg", "身份证号已存在!");
            return resJson;
        }

        Schoolfellow sf = new Schoolfellow();
        sf.setsName(name);
        sf.setsNum(s_num);
        sf.setIdNum(id_num);
        sf.setPhone(phone);
        sf.setIsPass(0);
        sf.setsPic("");
        sf.setUserName(userName);
        sfRepo.save(sf);

        resJson.put("status", true);
        resJson.put("msg", "申请成功!");
        return resJson;
    }

    @GetMapping(value = "/getSfBySname")
    public JSONObject getBySf(@RequestParam("sName") String sName) {
        List<Schoolfellow> sfList = sfRepo.getSfBySfName(sName);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(sfList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getSfBySnum")
    public JSONObject getSfByCname(@RequestParam("sNum") String sNum) {
        List<Schoolfellow> sfList = sfRepo.getSfBySfNum(sNum);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(sfList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getSfByIdNum")
    public JSONObject getSfByIdNum(@RequestParam("idNum") String idNum) {
        List<Schoolfellow> sfList = sfRepo.getSfBySfIdNum(idNum);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(sfList, resJson, index);

        return resJson;
    }

    @GetMapping(value = "/getSfByPhone")
    public JSONObject getSfByPhone(@RequestParam("phone") String phone) {
        List<Schoolfellow> sfList = sfRepo.getSfBySfPhone(phone);
        JSONObject resJson = new JSONObject();

        Integer index = 0;
        makeResJson(sfList, resJson, index);

        return resJson;
    }

    private void makeResJson(List<Schoolfellow> sfList, JSONObject resJson, Integer index) {
        if (sfList.size() != 0) {
            for (Schoolfellow sf : sfList) {
                JSONObject data_list = new JSONObject();
                data_list.put("sfName", sf.getsName());
                data_list.put("sfNum", sf.getsNum());
                data_list.put("idNum", sf.getIdNum());
                data_list.put("phone", sf.getPhone());
                resJson.put("data" + index.toString(), data_list);
                index++;
            }
        }
    }

}
