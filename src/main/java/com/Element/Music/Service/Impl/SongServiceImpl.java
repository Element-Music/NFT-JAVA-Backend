package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Service.MusicianService;
import com.Element.Music.Service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private MusicianService musicianService;

    public SongServiceImpl(MusicianService musicianService) {
        this.musicianService = musicianService;
    }

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
