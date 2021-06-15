package com.Element.Music.Service;

import com.Element.Music.Exception.MusicianException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Model.DAO.UserDAO.Musician;

import java.util.List;

public interface MusicianService {

    Musician addMusician(String accountId, boolean isAI);

    Musician getMusicianById(long id);

    Musician getMusicianByAccountId(String accountId);

    List<Musician> getAllMusician();

    boolean removeById(long id);

}
