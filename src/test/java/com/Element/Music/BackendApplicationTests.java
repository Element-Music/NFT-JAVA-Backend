package com.Element.Music;

import com.Element.Music.Emun.MusicType;
import com.Element.Music.Emun.Profession;
import com.Element.Music.Emun.Sex;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Service.MusicianService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private MusicianService musicianService;

    @Test
    void contextLoads() {
        Song song = Song.builder().audioPath("audioPath").createTime(new Date(System.currentTimeMillis())).description("description")
                .musicType(MusicType.JAZZ).lyric("lyric").name("song").representImagePath("imagePath").updateTime(new Date(System.currentTimeMillis()))
                .url("http://...com").build();
        Set<Song> songs = new HashSet<>();
        songs.add(song);
        Musician musician = Musician.builder().musicType(MusicType.JAZZ).description("这是一个歌手").profession(Profession.DOCTOR)
                .representativeWork("lujing").representImagePath("imagelujing").songs(songs).weibo("weibo").build();
        musician.setName("name");
        musician.setBirth(new Date(1995, 10, 29));
        musician.setEmail("740612415@qq.com");
        musician.setLocation("China");
        musician.setSex(Sex.FEMALE);
        musician.setPhoneNum("13151081251");
        musician.setPortrait("imagePath");
        musicianService.addMusician(musician);
    }

}
