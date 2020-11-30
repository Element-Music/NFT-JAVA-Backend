package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.MusicianException;
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
        return optionalMusician.get();
    }

    @Override
    public List<Musician> getMusicianByName(String name) throws MusicianException {
        if (name == null)
            throw new MusicianException("通过名字获取音乐家接口缺失name");
        return musicianRepository.findByDeletedIsFalseAndNameLike("%" + name + "%");
    }


    @Override
    public boolean deleteMusician(long id) {
        Optional<Musician> musicianOptional = musicianRepository.findById(id);
        if (musicianOptional.get() != null) {
            Musician musician = musicianOptional.get();
            musician.setDeleted(true);
            musicianRepository.save(musician);
            return true;
        }
        return false;
    }

    @Override
    public Musician updateMusicianMsg(Musician musician) {
        return null;
    }

    @Override
    public boolean updateSingerPic(Musician musician) throws MusicianException {
        if (musician == null || musician.getPortrait() == null) {
            if (musician == null)
                throw new MusicianException("更改图片接口缺失musician");
            else throw new MusicianException("更改图片接口缺失portrait");
        }
        Optional<Musician> musicianOptional = musicianRepository.findById(musician.getId());
        if (musicianOptional.get() != null || musicianOptional.get().isDeleted() == false) {
            Musician musician1 = musicianOptional.get();
            musician1.setPortrait(musician.getPortrait());
            musicianRepository.save(musician1);
            return true;
        }
        return false;
    }

    @Override
    public List<Musician> getAllMusician() {
        return musicianRepository.findAll();
    }

    @Override
    public boolean removeById(long id) {
        musicianRepository.deleteById(id);
        return musicianRepository.findById(id).get() == null;
    }


}