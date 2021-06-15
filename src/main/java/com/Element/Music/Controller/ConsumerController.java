package com.Element.Music.Controller;

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
import java.util.Date;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService; //null

    @Value("${consumer_portrait.path}")
    private String consumerPortrait;

    @Value("${user.path}")
    private String userPath;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        // add picture function later
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/consumerPic/**").addResourceLocations("file:" + consumerPortrait);
        }
    }

    private JSONObject checkAccountIdNotVoid(String accountId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", "钱包地址为空");
        return jsonObject;
    }


    private int addUserHelper(String accountId) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = new Consumer();
        consumer.setAccountId(accountId);
        consumer.setEmail(null);
        String portraitPath = "/img/consumerPic/1623567820637default_profile.jpg";
        consumer.setPortrait(portraitPath);
        consumer.setNickname("Unnamed");
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());
        return consumerService.addConsumer(consumer);
    }


    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String accountId = req.getParameter("accountId").trim();
        JSONObject jsonObject = new JSONObject();
        if (accountId.equals("")) {
            return checkAccountIdNotVoid(accountId);
        }
        int addConsumerRes = addUserHelper(accountId);
        if (addConsumerRes == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册成功");
        } else if (addConsumerRes == 1) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "该用户钱包已存在");
        }
        return jsonObject;
    }


    //    更改用户邮箱
    @ResponseBody
    @RequestMapping(value = "/update/email", method = RequestMethod.POST)
    public Object updateEmail(HttpServletRequest req) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String accountId = req.getParameter("accountId").trim();
        String email = req.getParameter("email").trim();

        if (accountId.equals("") || email.equals("")) {
            if (accountId.equals("")) {
                jsonObject = checkAccountIdNotVoid(accountId);
            } else {
                jsonObject.put("code", 2);
                jsonObject.put("msg", "用户邮箱为空");
            }
            return jsonObject;
        }
        int res = consumerService.updateConsumerEmail(email, accountId);
        if(res == 1) {
            jsonObject.put("code", 3);
            jsonObject.put("msg", "用户邮箱已被注册");
        }else if(res == 2) {
            jsonObject.put("code", 4);
            jsonObject.put("msg", "该用户不存在");
        }else if(res == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "更改邮箱成功");
        }else{
            jsonObject.put("code", 5);
            jsonObject.put("msg", "更改邮箱失败，系统错误");
        }
        return jsonObject;
    }


    //    更改用户昵称
    @ResponseBody
    @RequestMapping(value = "/update/nickname", method = RequestMethod.POST)
    public Object updateNickname(HttpServletRequest req) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        String accountId = req.getParameter("accountId").trim();
        String nickname = req.getParameter("nickname").trim();

        if (accountId.equals("") || nickname.equals("")) {
            if (accountId.equals("")) {
                jsonObject = checkAccountIdNotVoid(accountId);
            } else {
                jsonObject.put("code", 2);
                jsonObject.put("msg", "用户昵称为空");
            }
            return jsonObject;
        }
        int res = consumerService.updateConsumerNickname(nickname, accountId);
        if(res == 1) {
            jsonObject.put("code", 3);
            jsonObject.put("msg", "用户昵称已被注册");
        }else if(res == 2) {
            jsonObject.put("code", 4);
            jsonObject.put("msg", "该用户不存在");
        }else if(res == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "更改昵称成功");
        }else{
            jsonObject.put("code", 5);
            jsonObject.put("msg", "更改昵称失败，系统错误");
        }
        return jsonObject;
    }


    //    更新用户头像
    @ResponseBody
    @RequestMapping(value = "/update/portrait", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile pictureFile, @RequestParam("accountId") String accountId) {
        //TODO: wrap into global function to reduce duplicity
        JSONObject jsonObject = new JSONObject();

        if (accountId.equals("") || pictureFile.isEmpty()) {
            if (accountId.equals("")) {
                jsonObject = checkAccountIdNotVoid(accountId);
            }
            if (pictureFile.isEmpty()) {
                jsonObject.put("code", 2);
                jsonObject.put("msg", "用户头像为空！");
            }
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
            boolean res = consumerService.updateConsumerPicture(portraitPath, accountId);
            if (res) {
                jsonObject.put("code", 0);
                jsonObject.put("portrait", portraitPath);
                jsonObject.put("msg", "上传成功");
            } else {
                jsonObject.put("code", 3);
                jsonObject.put("msg", "上传失败");
            }
        } catch (IOException | ConsumerException e) {
            jsonObject.put("code", 3);
            jsonObject.put("msg", "上传失败" + e.getMessage());
        }
        return jsonObject;
    }


    //    返回指定ID的用户
    @RequestMapping(value = "/detail/id", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.getConsumerByID(Long.parseLong(id));
    }


    //    返回指定accountId的用户
    @RequestMapping(value = "/detail/accountId", method = RequestMethod.GET)
    public Object userOfAccountId(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String accountId = req.getParameter("accountId");
        if (accountId.equals("")) {
            jsonObject = checkAccountIdNotVoid(accountId);
            return jsonObject;
        }
        Consumer consumer = consumerService.getConumserByAccountId(accountId);
        if (consumer == null) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "该用户不存在");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "成功返回该用户");
            jsonObject.put("nickname", consumer.getNickname());
            jsonObject.put("portrait", consumer.getPortrait());
            jsonObject.put("email", consumer.getEmail());
            jsonObject.put("id", consumer.getId());
        }
        return jsonObject;
    }


    //    判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest req, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException, ConsumerException {
        String accountId = req.getParameter("accountId");
        JSONObject jsonObject = new JSONObject();
        if (accountId.equals("")) {
            return checkAccountIdNotVoid(accountId);
        }
        Consumer consumer = consumerService.getConumserByAccountId(accountId);
        if (consumer == null) {
            int addConsumerRes = addUserHelper(accountId);
            if(addConsumerRes == 0){
                consumer = consumerService.getConumserByAccountId(accountId);
                if (consumer == null) {
                    jsonObject.put("code", 3);
                    jsonObject.put("msg", "该用户不存在");
                    return jsonObject;
                }
            }else{
                jsonObject.put("code", 2);
                jsonObject.put("msg", "添加新用户失败");
            }
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "成功登录该用户");
        jsonObject.put("nickname", consumer.getNickname());
        jsonObject.put("portrait", consumer.getPortrait());
        jsonObject.put("email", consumer.getEmail());
        jsonObject.put("id", consumer.getId());
        String sessionId = session.getId();
        jsonObject.put("sessionId", sessionId);
        return jsonObject;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
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

    //    删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        JSONObject jsonObject = new JSONObject();
        if (!consumerService.removeById(Long.parseLong(id))) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除用户失败");
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "删除用户成功");
    }
}



