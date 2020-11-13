package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.UserRepository.MusicianRepository;
import com.Element.Music.Service.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MusicianServiceImpl implements MusicianService {

    @Autowired
    private MusicianRepository musicianRepository;

    @Override
    public Musician addMusician(Musician musician) {
        musicianRepository.save(musician);
        return null;
    }

    @Override
    public Musician getMusicianById(long id) {
        Optional<Musician> optionalMusician = musicianRepository.findById(id);
        return optionalMusician.get();
    }

    @Override
    public List<Musician> getMusicianByName(String name) {
        return null;
    }


    @Override
    public boolean deleteMusician() {
        return false;
    }

    @Override
    public Musician updateMusicianMsg(Musician musician) {
        return null;
    }

    @Override
    public boolean updateSingerPic(Musician musician) {
        return false;
    }


}
