package com.Element.Music.Service;

import com.Element.Music.Model.DAO.UserDAO.Musician;

import java.util.List;

public interface MusicianService {
    Musician addMusician(Musician musician);

    Musician getMusicianById(long id);

    List<Musician> getMusicianByName(String name);

    boolean deleteMusician();

    Musician updateMusicianMsg(Musician musician);

    boolean updateSingerPic(Musician musician);
}
