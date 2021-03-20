package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.SongException;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.MusicRepository.SongRepository;
import com.Element.Music.Service.MusicianService;
import com.Element.Music.Service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    private final MusicianService musicianService;

    public SongServiceImpl(MusicianService musicianService, SongRepository songRepository) {

        this.songRepository = songRepository;

        this.musicianService = musicianService;
    }

    @Override
    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    public boolean deleteSong(long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.orElse(null) != null) {
            Song song = songOptional.orElse(null);
            song.setDeleted(true);
            songRepository.save(song);
            return true;
        }
        return false;
    }

    @Override
    public List<Song> getAllSong() {
        return songRepository.findAll();
    }

    @Override
    public Song getSongById(long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        return songOptional.orElse(null);
    }

    @Override
    public List<Song> getSongByName(String name) {
        return songRepository.findAllBySongNameAndDeletedIsFalse(name);
    }

    @Override
    public List<Song> getSongsByMusician(long musicianId) {
//        return songRepository.findAllByMusician(musicianService.getMusicianById(musicianId));
        return songRepository.findAllByMusicianAndDeletedIsFalse(getMusicianById(musicianId));
    }

    @Override
    public boolean updateSong(Song song) throws SongException {
        if (song == null)
            throw new SongException("更改歌曲接口缺失song");
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.get() != null || !songOptional.get().isDeleted()) {
            Song song1 = songOptional.orElse(null);
            song1.setId(song.getId());
            song1.setSongName(song.getSongName());
            song1.setMusician(song.getMusician());
            song1.setDescription(song.getDescription());
            song1.setLyric(song.getLyric());
            songRepository.save(song1);
            return true;
        }
        return false;
    }

//    @Override
//    public void updateSongPrice(Song song, Double price) {
//        song.setPrice(price);
//        songRepository.save(song);
//    }

    @Override
    public boolean updateSongPic(Song song) throws SongException {
        if (song == null || song.getRepresentImagePath() == null) {
            if (song == null)
                throw new SongException("更改图片接口缺失song");
            else throw new SongException("更改图片接口缺失represent_image_path");
        }
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.orElse(null) != null || !songOptional.orElse(null).isDeleted()) {
            Song song1 = songOptional.orElse(null);
            song1.setRepresentImagePath(song.getRepresentImagePath());
            songRepository.save(song1);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSongUrl(Song song) throws SongException {
        if (song == null || song.getUrl() == null) {
            if (song == null)
                throw new SongException("更改URL接口缺失song");
            else throw new SongException("更改URL接口缺失url");
        }
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.orElse(null) != null || songOptional.orElse(null).isDeleted() == false) {
            Song song1 = songOptional.orElse(null);
            song1.setUrl(song.getUrl());
            songRepository.save(song1);
            return true;
        }
        return false;
    }

    @Override
    public Musician getMusicianById(long id) {
        Musician musician = musicianService.getMusicianById(id);
        return musician;
    }

    @Override
    public String getSongPic(long id) {
        Song song = songRepository.findById(id).get();
        if (song == null)
            return "这首歌不存在，发错id啦同学";
        String songPicPath = song.getRepresentImagePath();
        if (songPicPath == null || songPicPath.length() == 0)
            return "抱歉同学，这首歌的图片可能有某个傻瓜忘了存";
        return songPicPath;
    }
}
