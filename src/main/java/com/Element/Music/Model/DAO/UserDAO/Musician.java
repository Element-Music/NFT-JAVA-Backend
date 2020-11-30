package com.Element.Music.Model.DAO.UserDAO;


import com.Element.Music.Emun.MusicType;
import com.Element.Music.Emun.Profession;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Data
@Builder
@Entity
@Table(name = "musician")
@AllArgsConstructor
@NoArgsConstructor
public class Musician extends User implements Serializable {

    private static final long serialVersionUID = 7659253546867155512L;

//    @Id
//    @GeneratedValue
//    private long musicianId;

    @Column(nullable = false)
    private MusicType musicType;

    @OneToMany(mappedBy = "musician", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<Song> songs;

    private String weibo;

    private String description;

    private Profession profession;

    private String representativeWork;

    private String representImagePath;

    private int liked;

}
