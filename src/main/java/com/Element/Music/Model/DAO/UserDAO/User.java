package com.Element.Music.Model.DAO.UserDAO;

import com.Element.Music.Emun.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@NoArgsConstructor
@Data
@Entity
public class User {

    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected long id;

    @NonNull
    protected String name;

    @NonNull
    protected Date birth;

    @NonNull
    protected String location;

    @NonNull
    protected String passWord;

    protected String picture;

    protected Date createTime;

    protected Date updateTime;

    protected Sex sex;


    protected String phoneNum;

    protected String email;
}