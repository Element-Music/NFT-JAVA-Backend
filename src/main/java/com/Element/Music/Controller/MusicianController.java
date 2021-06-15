package com.Element.Music.Controller;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Service.MusicianService;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/musician")
public class MusicianController {

    private final MusicianService musicianService;


    public MusicianController(MusicianService musicianService) { this.musicianService = musicianService; }


    private JSONObject checkAccountIdNotVoid(String accountId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("msg", "钱包地址为空");
        return jsonObject;
    }


    //    添加歌手
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String accountId = req.getParameter("accountId").trim();
        String ai = req.getParameter("ai").trim();
        if (accountId.equals("")) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "用户名钱包为空");
            return jsonObject;
        }
        if(!ai.equals("0") && !ai.equals("1") ) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "未定义音乐人是否是AI");
            return jsonObject;
        }
        boolean isAI = ai.equals("1");
        Musician addMusicianRes = musicianService.addMusician(accountId, isAI);
        if(addMusicianRes != null){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "新增成功");
        } else {
            jsonObject.put("code", 3);
            jsonObject.put("msg", "新增失败");
        }
        return jsonObject;
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
        Musician musician = musicianService.getMusicianByAccountId(accountId);
        if (musician == null) {
            jsonObject.put("code", 2);
            jsonObject.put("msg", "该用户不存在");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "成功返回该用户");
            jsonObject.put("isAI", musician.isAI());
            jsonObject.put("consumer", musician.getConsumer());
            jsonObject.put("id", musician.getId());
        }
        return jsonObject;
    }

    //    返回指定id的用户
    @RequestMapping(value = "/detail/id", method = RequestMethod.GET)
    public Object BalanceOfUserId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return musicianService.getMusicianById(Long.parseLong(id));
    }

    //    返回所有歌手
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object allSinger() {
        return musicianService.getAllMusician();
    }


    //    删除歌手
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteSinger(HttpServletRequest req) {
        String id = req.getParameter("id");
        JSONObject jsonObject = new JSONObject();
        if (!musicianService.removeById(Long.parseLong(id))) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除音乐人失败");
        }
        jsonObject.put("code", 0);
        jsonObject.put("msg", "删除音乐人成功");
        return jsonObject;
    }

//    //    更新歌手信息
//    @ResponseBody
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public Object updateSingerMsg(HttpServletRequest req) throws MusicianException {
//        JSONObject jsonObject = new JSONObject();
//        String id = req.getParameter("id").trim();
//        String name = req.getParameter("name").trim();
//        String sex = req.getParameter("sex").trim();
//        //String pic = req.getParameter("pic").trim();
//        String birth = req.getParameter("birth").trim();
//        String location = req.getParameter("location").trim();
//        String description = req.getParameter("description").trim();
//
//        Musician musician = Musician.builder().description(description).build();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date myBirth;
//        try {
//            myBirth = dateFormat.parse(birth);
//        } catch (Exception e) {
//            jsonObject.put("code", 2);
//            jsonObject.put("msg", "生日日期格式错误");
//            return jsonObject;
//        }
//        musician.setName(name);
//        musician.setBirth(myBirth);
//        musician.setLocation(location);
//        musician.setSex(Sex.valueOf(sex));
//        musician.setId(Long.parseLong(id));
//
//        boolean res = musicianService.updateMusicianMsg(musician);
//        if (res) {
//            jsonObject.put("code", 0);
//            jsonObject.put("msg", "修改成功");
//        } else {
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "修改失败");
//        }
//        return jsonObject;
//    }


    @RequestMapping(value = "/id/detail", method = RequestMethod.GET)
    public Object getMusicianById(HttpServletRequest req) {
        long id = Long.parseLong(req.getParameter("musicianId"));
        return musicianService.getMusicianById(id);
    }
}