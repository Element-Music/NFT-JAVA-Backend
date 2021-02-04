package com.Element.Music.Model.DAO.MusicDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Song")
@Data
@Builder
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
public class Album extends BaseEntity {

    @OneToMany()
    private Set<Song> songs;

    @ManyToOne()
    private Musician musician;


}
