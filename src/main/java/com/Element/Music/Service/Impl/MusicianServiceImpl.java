package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.UserRepository.MusicianRepository;
import com.Element.Music.Service.MusicianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicianServiceImpl implements MusicianService {

    private final MusicianRepository musicianRepository;

    public MusicianServiceImpl(MusicianRepository musicianRepository) {
        this.musicianRepository = musicianRepository;
    }

    @Override
    public Musician addMusician(Musician musician) {
        return musicianRepository.save(musician);
    }

    @Override
    public Musician getMusicianById(long id) {
        Optional<Musician> optionalMusician = musicianRepository.findById(id);
        return optionalMusician.orElse(null);
    }

    @Override
    public List<Musician> getMusicianByName(String name) {
        return musicianRepository.findAllByName(name);
    }


    @Override
    public boolean deleteMusician(Musician musician) {
        musicianRepository.delete(musician);
        return musicianRepository.findAll().contains(musician);
    }

    @Override
    public Musician updateMusicianMsg(Musician musician) {
        return null;
    }

    @Override
    public boolean updateSingerPic(Musician musician) {

        return false;
    }

    @Override
    public List<Musician> getAllMusician() {
        return musicianRepository.findAll();
    }

    @Override
    public boolean removeById(long id) {
        musicianRepository.deleteById(id);
        return musicianRepository.findById(id).isEmpty();
    }


}
