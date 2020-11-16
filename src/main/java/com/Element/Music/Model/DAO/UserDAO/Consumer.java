package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Model.DAO.MusicDAO.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "musician", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)//TODO，了解各种cascade的作用
    private Set<Song> collections;

}
