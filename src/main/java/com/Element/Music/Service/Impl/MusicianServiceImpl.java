package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.MusicianException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.Element.Music.Repository.UserRepository.MusicianRepository;
import com.Element.Music.Service.ConsumerService;
import com.Element.Music.Service.MusicianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MusicianServiceImpl implements MusicianService {

    private final MusicianRepository musicianRepository;

    private final ConsumerService consumerService;

    public MusicianServiceImpl(MusicianRepository musicianRepository, ConsumerService consumerService) {

        this.musicianRepository = musicianRepository;
        this.consumerService = consumerService;
    }

    @Override
    public Musician addMusician(String accountId, boolean isAI) {
        Musician musician = new Musician();
        musician.setConsumer(consumerService.getConumserByAccountId(accountId));
        musician.setAI(isAI);
        return musicianRepository.save(musician);
    }

    @Override
    public Musician getMusicianById(long id) {
        Optional<Musician> optionalMusician = musicianRepository.findById(id);
        return optionalMusician.orElse(null);
    }

    @Override
    public Musician getMusicianByAccountId(String accountId){
        Consumer targetConsumer = consumerService.getConumserByAccountId(accountId);
        Musician res = musicianRepository.findByConsumerAndDeletedIsFalse(targetConsumer);
        if(res == null) return null;
        return res;
    }


//    @Override
//    public boolean updateMusicianMsg(Musician musician) throws MusicianException {
//        if (musician == null) throw new MusicianException("更改歌手接口缺失musician");
//        Optional<Musician> musicianOptional = musicianRepository.findById(musician.getId());
//        if (musicianOptional.isEmpty()) return false;
//        if (!musicianOptional.get().isDeleted()) {
//            Musician musician1 = musicianOptional.orElse(null);
//            musician1.setId(musician.getId());
//            musician1.setAI(musician.isAI());
//            musicianRepository.save(musician1);
//            return true;
//        }
//        return false;
//    }

    @Override
    public List<Musician> getAllMusician() {
        return musicianRepository.findAll();
    }

    @Override
    public boolean removeById(long id) {
        Optional<Musician> musicianOptional = musicianRepository.findById(id);
        if (musicianOptional.isEmpty()) return false;
        Musician musician = musicianOptional.get();
        musician.setDeleted(true);
        musicianRepository.save(musician);
        return true;
    }

}