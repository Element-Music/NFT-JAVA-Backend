package com.Element.Music.Repository.MusicRepository;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

//    List<Song> findAllByName(String song);

    List<Song> findAllByMusician(long musicianId);

    Musician findMusicianById(long musicianId);
}