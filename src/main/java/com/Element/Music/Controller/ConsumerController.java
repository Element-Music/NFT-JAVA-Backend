package com.Element.Music.Controller;

import com.Element.Music.Emun.Sex;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Service.ConsumerService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/portrait/**").addResourceLocations("file:/Users/jiangjiayi/Documents/Element/server/portrait/");
        }
    }

    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phoneNum = req.getParameter("phoneNum").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String portrait = req.getParameter("portrait").trim();

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.setName(username);
        consumer.setPassWord(password);
        consumer.setSex(sex.equals("male") ? Sex.MALE : Sex.FEMALE);
        if (phoneNum == "") {
            consumer.setPhoneNum(null);
        } else {
            consumer.setPhoneNum(phoneNum);
        }

        if (email == "") {
            consumer.setEmail(null);
        } else {
            consumer.setEmail(email);
        }
        consumer.setBirth(myBirth);
        consumer.setLocation(location);
        consumer.setPortrait(portrait);
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());

        Consumer res = consumerService.addConsumer(consumer);
        if (res != null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "注册成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败");
            return jsonObject;
        }
    }

    //    判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/login/username", method = RequestMethod.POST)
    public Object userNameLogin(HttpServletRequest req, HttpSession session) {

        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean res = consumerService.verifyPasswdByPhoneNum(username, password);

        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            //jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("username", username);
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login/phoneNum",method = RequestMethod.POST)
    public Object phoneNumLogin(HttpServletRequest req, HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String phoneNum = req.getParameter("phoneNum");
        String password = req.getParameter("password");
        boolean res = consumerService.verifyPasswdByUserName(phoneNum, password);

        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            //jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("phoneNum", phoneNum);
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
    }

    //    返回所有用户
    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
    public JSONArray allUser() {
        return JSONArray.parseArray(JSON.toJSONString(consumerService.getAllUser()));
    }

    //    返回指定ID的用户
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public JSONObject userOfId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return (JSONObject) JSONObject.toJSON(consumerService.getConsumerByID(Long.parseLong(id)));
    }

    //    删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.removeById(Integer.parseInt(id));
    }

    //    更新用户信息
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateUserMsg(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phoneNum = req.getParameter("phoneNum").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String portrait = req.getParameter("portrait").trim();

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setName(username);
        consumer.setPassWord(password);
        consumer.setSex(sex.equals("male") ? Sex.MALE : Sex.FEMALE);
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setLocation(location);
        consumer.setPortrait(portrait);
        consumer.setUpdateTime(new Date());

        Consumer res = consumerService.updateConsumer(consumer);
        if (res != null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

    //    更新用户头像
    @ResponseBody
    @RequestMapping(value = "/portrait/update", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile pictureFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();

        if (pictureFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + pictureFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "portrait";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String portraitPath = "/portrait/" + fileName;
        try {
            pictureFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setPortrait(portraitPath);
            boolean res = consumerService.updateUserPicture(consumer);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("portrait", portraitPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
            return jsonObject;
        } finally {
            return jsonObject;
        }
    }
}
