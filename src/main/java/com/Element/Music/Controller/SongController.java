package com.Element.Music.Controller;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Service.SongService;
import com.Element.Music.Util.FileUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大15M,DataUnit提供5中类型B,KB,MB,GB,TB
        factory.setMaxFileSize(DataSize.of(15, DataUnit.MEGABYTES));
        /// 设置总上传数据总大小15M
        factory.setMaxRequestSize(DataSize.of(15, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/songPic/**").addResourceLocations("file:/Users/jiangjiayi/Documents/Element/server/img/songPic/");
            registry.addResourceHandler("/song/**").addResourceLocations("file:/Users/jiangjiayi/Documents/Element/server/song/");
        }
    }

    //    添加歌曲
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSong(HttpServletRequest req, @RequestParam("file") MultipartFile mpfile) {
        JSONObject jsonObject = new JSONObject();
        String musicianId = req.getParameter("musicianId").trim();
        String name = req.getParameter("name").trim();
        String description = req.getParameter("description").trim();
        String pic = "/img/songPic/tubiao.jpg";
        String lyric = req.getParameter("lyric").trim();

        if (mpfile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "音乐上传失败！");
            return jsonObject;
        }
        String fileName = mpfile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            mpfile.transferTo(dest);
            Song song = Song.builder().musician(songService.getMusicianById(Long.parseLong(musicianId))).description(description).name(name)
                    .lyric(lyric).representImagePath(pic).build();
            //song.setUrl(storeUrlPath);
            Song res = songService.addSong(song);
            if (res != null) {
                jsonObject.put("code", 1);
                jsonObject.put("avator", storeUrlPath);
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

    //    返回所有歌曲
/*    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSong(){
        return songService.allSong();
    }

    //    返回指定歌曲ID的歌曲
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object songOfId(HttpServletRequest req){
        String id = req.getParameter("id");
        return songService.songOfId(Integer.parseInt(id));
    }

    //    返回指定歌手ID的歌曲
    @RequestMapping(value = "/singer/detail", method = RequestMethod.GET)
    public Object songOfSingerId(HttpServletRequest req){
        String singerId = req.getParameter("singerId");
        return songService.songOfSingerId(Integer.parseInt(singerId));
    }

    //    返回指定歌手名的歌曲
    @RequestMapping(value = "/singerName/detail", method = RequestMethod.GET)
    public Object songOfSingerName(HttpServletRequest req){
        String name = req.getParameter("name");
        return songService.songOfSingerName('%'+ name + '%');
    }

    //    返回指定歌曲名的歌曲
    @RequestMapping(value = "/name/detail", method = RequestMethod.GET)
    public Object songOfName(HttpServletRequest req){
        String name = req.getParameter("name").trim();
        return songService.songOfName(name);
    }

    //    删除歌曲
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object deleteSong(HttpServletRequest req){
        String id = req.getParameter("id");
        return songService.deleteSong(Integer.parseInt(id));
    }*/

    //    更新歌曲信息
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateSongMsg(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String musicianId = req.getParameter("singerId").trim();
        String name = req.getParameter("name").trim();
        String description = req.getParameter("description").trim();
        String lyric = req.getParameter("lyric").trim();

        Song song = Song.builder().musician(songService.getMusicianById(Long.parseLong(musicianId))).description(description).name(name)
                .lyric(lyric).build();
        song.setId(Long.parseLong(id));
        song.setLyric(lyric);

        Song res = songService.updateSong(song);
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

    //    更新歌曲图片
    @ResponseBody
    @RequestMapping(value = "/img/update", method = RequestMethod.POST)
    public Object updateSongPic(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") long id) {
        JSONObject jsonObject = new JSONObject();

        if (urlFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "音乐上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "songPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeImagePath = "/img/songPic/" + fileName;
        try {
            urlFile.transferTo(dest);
            Song song = Song.builder().representImagePath(storeImagePath).build();
            song.setId(id);
            Song res = songService.updateSongPic(song);
            if (res != null) {
                jsonObject.put("code", 1);
                jsonObject.put("avator", storeImagePath);
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

    //    更新歌曲URL
    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/url/update", method = RequestMethod.POST)
    public Object updateSongUrl(@RequestParam("file") MultipartFile urlFile, @RequestParam("id") long id) {
        JSONObject jsonObject = new JSONObject();

        if (urlFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "音乐上传失败！");
            return jsonObject;
        }
        String fileName = urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            urlFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeUrlPath);
            boolean res = songService.updateSongUrl(song);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("urlPath", storeUrlPath);
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

    @RequestMapping(value = "/download")
    public void download(@RequestParam("fileName") String filename) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        String type = new MimetypesFileTypeMap().getContentType(filename);
        response.setHeader("Content-type", type);
        String header = new String(filename.getBytes("utf-8"), "iso-8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + header);
        FileUtils.download(filename, response);
    }
}
