package com.Element.Music.Model.DAO.UserDAO;


import com.Element.Music.Emun.MusicType;
import com.Element.Music.Emun.Profession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "")
@AllArgsConstructor
public class Musician extends User implements Serializable {

    private static final long serialVersionUID = 7659253546867155512L;

    @Column(nullable = false)
    private MusicType musicType;

    private String weibo;

    private String description;

    private Profession profession;

    private String representativeWork;

    private String representImagePath;

}
