package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.SongException;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.MusicRepository.SongRepository;
import com.Element.Music.Repository.UserRepository.MusicianRepository;
import com.Element.Music.Service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public boolean deleteSong(long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        if (songOptional.get() != null) {
            Song song = songOptional.get();
            song.setDeleted(true);
            songRepository.save(song);
            return true;
        }
        return false;
    }

    @Override
    public Song getSongById(long id) {
        Optional<Song> songOptional = songRepository.findById(id);
        return songOptional.get();
    }

    @Override
    public List<Song> getSongsByMusician(long musicianId) {

        return songRepository.findAllByMusician(musicianId);
    }

    @Override
    //need check
    public boolean updateSong(Song song) throws SongException{
        if(song == null)
            throw new SongException("更改歌曲接口缺失song");
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.get() != null || songOptional.get().isDeleted() == false) {
            Song song1 = songOptional.get();
            song1.setId(song.getId());
            songRepository.save(song1);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSongPic(Song song) throws SongException {
        if (song == null || song.getRepresentImagePath() == null) {
            if (song == null)
                throw new SongException("更改图片接口缺失song");
            else throw new SongException("更改图片接口缺失represent_image_path");
        }
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.get() != null || songOptional.get().isDeleted() == false) {
            Song song1 = songOptional.get();
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
                throw new SongException("更改图片接口缺失song");
            else throw new SongException("更改图片接口缺失url");
        }
        Optional<Song> songOptional = songRepository.findById(song.getId());
        if (songOptional.get() != null || songOptional.get().isDeleted() == false) {
            Song song1 = songOptional.get();
            song1.setUrl(song.getUrl());
            songRepository.save(song1);
            return true;
        }
        return false;
    }

    @Override
    public Musician getMusicianById(long id) {
        Optional<Musician> optionalMusician = musicianRepository.findById(id);
        return optionalMusician.get();
    }
}
