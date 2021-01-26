package com.Element.Music.Controller;

import com.Element.Music.Emun.MusicType;
import com.Element.Music.Exception.MusicianException;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Service.MusicianService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/musician")
public class MusicianController {

    private final MusicianService musicianService;

    @Value("${musician_portrait.path}")
    private String musicianPortrait;

    @Value("${user.path}")
    private String userPath;

    public MusicianController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/singerPic/**").addResourceLocations("file:" + musicianPortrait);
        }
    }

    //    添加歌手
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String name = req.getParameter("name").trim();
        String sex = req.getParameter("sex").trim();
        String pic = req.getParameter("pic").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String description = req.getParameter("description").trim();
        String portrait = req.getParameter("portrait").trim();
        String musicType = req.getParameter("musicType").trim();

        Musician musician = Musician.builder().representImagePath(pic).musicType(MusicType.valueOf(musicType))
                .description(description).name(name).build();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        musician.setName(name);
        musician.setBirth(myBirth);
        musician.setLocation(location);
        musician.setSex(sex.equals("male") ? true : false);
        musician.setPortrait(portrait);
        Musician res = musicianService.addMusician(musician);

        if (res != null) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败");
            return jsonObject;
        }
    }

    //    返回所有歌手
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSinger() {
        return musicianService.getAllMusician();
    }

    //    根据歌手名查找歌手
    @RequestMapping(value = "/name/detail", method = RequestMethod.GET)
    public Object singerOfName(HttpServletRequest req) throws MusicianException {
        String name = req.getParameter("name").trim();
        return musicianService.getMusicianByName(name);
    }


    //    删除歌手
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSinger(HttpServletRequest req) {
        String id = req.getParameter("id");
        return musicianService.removeById(Long.parseLong(id));
    }

    //    更新歌手信息
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSingerMsg(HttpServletRequest req) throws MusicianException {
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String name = req.getParameter("name").trim();
        String sex = req.getParameter("sex").trim();
        String pic = req.getParameter("pic").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String description = req.getParameter("description").trim();

        Musician musician = Musician.builder().representImagePath(pic).description(description).build();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        musician.setName(name);
        musician.setBirth(myBirth);
        musician.setLocation(location);
        musician.setSex(sex.equals("male") ? true : false);
        musician.setId(Long.parseLong(id));

        boolean res = musicianService.updateMusicianMsg(musician);
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

    //    更新歌手头像
    @ResponseBody
    @RequestMapping(value = "/portrait/update", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile portraitFile, @RequestParam("id") long id) {
        JSONObject jsonObject = new JSONObject();

        if (portraitFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + portraitFile.getOriginalFilename();
        String filePath = userPath + "img";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        filePath += System.getProperty("file.separator") + "singerPic";
        File file2 = new File(filePath);
        if (!file2.exists()) {
            file2.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storePortraitPath = "/img/singerPic/" + fileName;

        try {
            portraitFile.transferTo(dest);
            Musician musician = Musician.builder().build();
            musician.setId(id);
            musician.setPortrait(storePortraitPath);
            boolean res = musicianService.updateSingerPic(musician);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("pic", storePortraitPath);
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

    @RequestMapping(value = "/MusicianPicture", method = RequestMethod.GET)
    public String getMusicianPortrait(HttpServletRequest req) {
        String id = req.getParameter("id");
        return musicianService.getMusicianPortrait(Long.parseLong(id));
    }
}