package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Emun.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    protected long id;

    @NonNull
    protected String name;

    @NonNull
    protected Date birth;

    @NonNull
    protected String location;

    protected String portrait;

    protected Date createTime;

    protected Date updateTime;

    protected Sex sex;

    protected String phoneNum;

    protected String email;
}