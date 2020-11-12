package com.Element.Music.Model.DAO.UserDAO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;


@NoArgsConstructor
@Data
public class User {

    @NonNull
    protected long id;

    @NonNull
    protected String userName;

    @NonNull
    protected Date birth;

    @NonNull
    protected String location;

    @NonNull
    protected String passWord;

    protected String picture;

    protected Date createTime;

    protected Date updateTime;

    protected Byte sex;


    protected String phoneNum;

    protected String email;
}