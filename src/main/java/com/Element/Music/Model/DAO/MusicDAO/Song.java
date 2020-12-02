package com.Element.Music.Model.DAO.MusicDAO;

import com.Element.Music.Emun.MusicType;
import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
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
@Table(name = "Song")
@NoArgsConstructor
@AllArgsConstructor
public class Song extends BaseEntity {

    @Column(nullable = false)
    private String musicianName;

    @Column(nullable = false)
    private String songName;

    private String representImagePath;

    private MusicType musicType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Musician musician;

    private String url;

    private String lyric;

    private String description;

    private int liked;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "collections")
    private Set<Consumer> consumers;
}
