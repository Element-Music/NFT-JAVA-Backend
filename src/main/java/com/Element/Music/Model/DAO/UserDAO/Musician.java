package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Data
@Builder
@Entity
@Table(name = "musician")
@AllArgsConstructor
@NoArgsConstructor
public class Musician extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7659253546867155512L;

    private boolean AI;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"musician"})
    private Consumer consumer;
}
