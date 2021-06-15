package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.UserDAO.Musician;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "consumer")
@AllArgsConstructor
@NoArgsConstructor

public class Consumer extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2214230518390003400L;

    @NonNull
    private String accountId;

    private String nickname;

    private String email;

    private String portrait;

    @OneToOne(mappedBy = "consumer")
    @JsonIgnoreProperties({"consumer"})
    private Musician musician;
}
