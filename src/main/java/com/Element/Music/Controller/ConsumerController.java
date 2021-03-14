package com.Element.Music.Controller;

//import com.Element.Music.Emun.Sex;

import com.Element.Music.Emun.Sex;
import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Service.ConsumerService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService; //null


    @Value("${consumer_portrait.path}")
    private String consumerPortrait;

    @Value("${user.path}")
    private String userPath;

    public ConsumerController(ConsumerService consumerService) { this.consumerService = consumerService; }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/consumerPic/**").addResourceLocations("file:" + consumerPortrait);
        }
    }

    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String phoneNum = req.getParameter("phoneNum").trim();
        String email = req.getParameter("email").trim();

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
        Consumer consumer = new Consumer();

        consumer.setName(username);
        consumer.setPassWord(password);
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
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        Consumer addConsumerRes = null;
        addConsumerRes = consumerService.addConsumer(consumer);

//        Purse addPurseRes = null;


        if (addConsumerRes != null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "注册成功");


//            PurseController purseController = new PurseController(purseService);
//
//            Object initializePurseRes = purseController.initializeBalance(addConsumerRes);
//            System.out.println("addConsumerRes");
//            System.out.println(addConsumerRes);
//            System.out.println("initializePurseRes");
//            System.out.println(initializePurseRes);
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败,该用户已存在");
        }
        return jsonObject;
    }

    //    判断是否登录成功
    @ResponseBody
    @Deprecated
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest req, HttpSession session) throws UnsupportedEncodingException {

        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int res = consumerService.verifyPasswdByUser(username, password);

        if (res == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "登录成功");
            //jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("username", username);
            String sessionId = session.getId();
            jsonObject.put("sessionId", sessionId);
            return jsonObject;
        } else if (res == 1) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "用户名格式错误");
            return jsonObject;
        } else if (res == 2) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "该账户未注册");
            return jsonObject;
        } else {
            jsonObject.put("code", 3);
            jsonObject.put("msg", "用户名和密码不匹配");
            return jsonObject;
        }
    }

    @RequestMapping(value = "/collection", method = RequestMethod.GET)
    public Object collect(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String songId = req.getParameter("songId");
        String consumerId = req.getParameter("consumerId");
        consumerService.addToCollection(Long.parseLong(consumerId), Long.parseLong(songId));
        return consumerService.getCollection(Long.parseLong(consumerId));
    }


//    @ResponseBody
//    @Deprecated
//    @RequestMapping(value = "/login/username", method = RequestMethod.POST)
//    public Object userNameLogin(HttpServletRequest req, HttpSession session) throws UnsupportedEncodingException {
//
//        JSONObject jsonObject = new JSONObject();
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        boolean res = consumerService.verifyPasswdByUserName(username, password);
//
//        if (res) {
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "登录成功");
//            //jsonObject.put("userMsg", consumerService.loginStatus(username));
//            session.setAttribute("username", username);
//            return jsonObject;
//        } else {
//            jsonObject.put("code", 0);
//            jsonObject.put("msg", "用户名或密码错误");
//            return jsonObject;
//        }
//    }
//
//
//
//    @ResponseBody
//    @RequestMapping(value = "/login/phoneNum", method = RequestMethod.POST)
//    public Object phoneNumLogin(HttpServletRequest req, HttpSession session) throws UnsupportedEncodingException {
//        JSONObject jsonObject = new JSONObject();
//        String phoneNum = req.getParameter("phoneNum");
//        String password = req.getParameter("password");
//        boolean res = consumerService.verifyPasswdByPhoneNum(phoneNum, password);
//
//        if (res) {
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "登录成功");
//            //jsonObject.put("userMsg", consumerService.loginStatus(username));
//            session.setAttribute("phoneNum", phoneNum);
//            return jsonObject;
//        } else {
//            jsonObject.put("code", 0);
//            jsonObject.put("msg", "用户名或密码错误");
//            return jsonObject;
//        }
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/login/email", method = RequestMethod.POST)
//    public Object emailLogin(HttpServletRequest req, HttpSession session) throws UnsupportedEncodingException {
//        JSONObject jsonObject = new JSONObject();
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        boolean res = consumerService.verifyPasswdByEmail(email, password);
//
//        if (res) {
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "登录成功");
//            //jsonObject.put("userMsg", consumerService.loginStatus(username));
//            session.setAttribute("email", email);
//            return jsonObject;
//        } else {
//            jsonObject.put("code", 0);
//            jsonObject.put("msg", "用户名或密码错误");
//            return jsonObject;
//        }
//    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", "成功下线");
        return jsonObject;
    }

    //    返回所有用户
//    @RequestMapping(value = "/allConsumer", method = RequestMethod.GET)
//    public JSONArray allUser() {
//        return JSONArray.parseArray(JSconsumerService.getAllUser()));
//    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSinger() {
        return consumerService.getAllUser();
    }

    //    返回指定ID的用户
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.getConsumerByID(Long.parseLong(id));
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
    public Object updateUserMsg(HttpServletRequest req) throws ConsumerException {
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
        consumer.setId(Long.parseLong(id));
        consumer.setName(username);
        consumer.setPassWord(password);
        consumer.setSex(Sex.valueOf(sex));
        consumer.setPhoneNum(phoneNum);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setLocation(location);
        consumer.setPortrait(portrait);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateConsumer(consumer);
        if (res) {
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
    public Object updateUserPic(@RequestParam("file") MultipartFile pictureFile, @RequestParam("id") long id) {
        JSONObject jsonObject = new JSONObject();

        if (pictureFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + pictureFile.getOriginalFilename();
        String filePath = userPath + "img";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        filePath += System.getProperty("file.separator") + "consumerPic";
        File file2 = new File(filePath);
        if (!file2.exists()) {
            file2.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String portraitPath = "/img/consumerPic/" + fileName;
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
