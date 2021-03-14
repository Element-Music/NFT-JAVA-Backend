package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.TradeDAO.ConsumerOrder;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "consumer")
@AllArgsConstructor
@NoArgsConstructor
public class Consumer extends User implements Serializable {

    private static final long serialVersionUID = -2214230518390003400L;

    private String nickName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Song> collections;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Song> mySongs;

    @NonNull
    private String passWord;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Song> playList;

}
