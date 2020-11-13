package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Service.MusicianService;
import com.Element.Music.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SongServiceImpl implements SongService {

    @Autowired
    private MusicianService musicianService;

    @Override
    public Song addSong(Song song) {
        return null;
    }

    @Override
    public Song deleteSong(long id) {
        return null;
    }

    @Override
    public Song getSongById(long id) {
        return null;
    }

    @Override
    public List<Song> getSongsByMusician(long musicianId) {
        return null;
    }

    @Override
    public Song updateSong(Song song) {
        return null;
    }

    @Override
    public Song updateSongPic(Song song) {
        return null;
    }

    @Override
    public boolean updateSongUrl(Song song) {
        return false;
    }

    @Override
    public Musician getMusicianById(long id) {
        return musicianService.getMusicianById(id);
    }


}
