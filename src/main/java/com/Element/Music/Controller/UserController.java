package com.Element.Music.Controller;

import com.Element.Music.Emun.Sex;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Service.ConsumerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

public class UserController {
    @Autowired
    private ConsumerService consumerService;

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/portrait/**").addResourceLocations("file:/Users/jiangjiayi/Documents/github-workspace/music-website/music-server/avatorImages/");
        }
    }

    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phoneNum = req.getParameter("phoneNum").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String picture = req.getParameter("picture").trim();

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
        consumer.setPicture(picture);
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
    @RequestMapping(value = "/user/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session) {

        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
//        System.out.println(username+"  "+password);
        boolean res = consumerService.verifyPasswd(username, password);

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

    //    返回所有用户
/*    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object allUser() {
        return consumerService.allUser();
    }

    //    返回指定ID的用户
    @RequestMapping(value = "/user/detail", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.userOfId(Integer.parseInt(id));
    }

    //    删除用户
    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.deleteUser(Integer.parseInt(id));
    }*/

    //    更新用户信息
    @ResponseBody
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Object updateUserMsg(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phoneNum").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();

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
        consumer.setPhoneNum(phone_num);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setLocation(location);
//        consumer.setAvator(avator);
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
    @RequestMapping(value = "/user/portrait/update", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile pictureFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();

        if (pictureFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + pictureFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "avatorImages";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storePicturePath = "/avatorImages/" + fileName;
        try {
            pictureFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setPicture(storePicturePath);
            boolean res = consumerService.updateUserPicture(consumer);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("avator", storePicturePath);
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
