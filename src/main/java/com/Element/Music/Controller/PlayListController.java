package com.Element.Music.Controller;

import com.Element.Music.Service.MusicianService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/playList")
public class PlayListController {

    private final MusicianService musicianService;

    @Value("${playList_picture.path}")
    private String musicianPortrait;

    @Value("${user.path}")
    private String userPath;

    public PlayListController(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

    @ResponseBody
    @RequestMapping("/getById")
    public Object getPlayListById() {
        return null;
    }

    @ResponseBody
    @RequestMapping("/getAll")
    public Object getAllPlayList() {
        return null;
    }

    @RequestMapping("/add")
    public Object addPlayList() {
        return null;
    }

    @RequestMapping("/remove")
    public Object removePlayList() {
        return null;
    }

}
