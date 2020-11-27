package com.Element.Music.Service;

import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface ConsumerService {

    Consumer logout();

    void delete(long ConsumerId);

    boolean verifyPasswdByEmail(String Email, String passWord);

    Consumer addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException;

    boolean verifyPasswdByUserName(String userName, String passWord);

    @Deprecated
    boolean verifyPasswdByPhoneNum(String phoneNum, String passWord);

    Consumer updateConsumer(Consumer consumer);

    boolean updateUserPicture(Consumer consumer) throws ConsumerException;

    Consumer getConsumerByID(long id);

    List<Consumer> getAllUser();

    boolean removeById(long id);
}
