package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.MusicRepository.SongRepository;
import com.Element.Music.Repository.UserRepository.MusicianRepository;
import com.Element.Music.Service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final MusicianRepository musicianRepository;

    private final SongRepository songRepository;

    public SongServiceImpl(MusicianRepository musicianRepository, SongRepository songRepository) {

        this.songRepository = songRepository;

        this.musicianRepository = musicianRepository;
    }

    @Override
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public void deleteSong(long id) {
        songRepository.deleteById(id);
    }

    @Override
    public Song getSongById(long id) {
        return songRepository.findById(id).get();
    }

    @Override
    public List<Song> getSongsByMusician(long musicianId) {
        return songRepository.findAllByMusician(musicianId);
    }

    @Override
    public Song updateSong(Song song) {
        return songRepository.save(song);
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
        return songRepository.findMusicianById(id);
    }
}
