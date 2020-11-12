package com.Element.Music.Service;

import com.Element.Music.Model.DAO.MusicDAO.Song;

import java.util.List;

public interface SongService {

    Song addSong(Song song);

    Song deleteSong(long id);

    Song getSongById(long id);

    List<Song> getSongsByMusician(long musicianId);

    Song updateSong(Song song);

    Song updateSongPic(Song song);

    boolean updateSongUrl(Song song);
}
