package com.Element.Music.Model.DAO.MusicDAO;

import com.Element.Music.Emun.MusicType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "")
public class Song {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    private long songId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date updateTime;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private String audioPath;

    private String representImagePath;

    private MusicType musicType;

    private Long musicianId;

    private String url;

    private String lyric;

    private String description;

}
