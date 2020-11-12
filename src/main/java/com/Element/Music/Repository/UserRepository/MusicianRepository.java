package com.Element.Music.Repository.UserRepository;

import com.Element.Music.Model.DAO.UserDAO.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long> {
    List<Musician> findAllByName(String name);
}
