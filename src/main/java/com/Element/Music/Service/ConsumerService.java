package com.Element.Music.Service;

import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public interface ConsumerService {

    Consumer logout();

    void delete(long ConsumerId);

    Consumer getConumserByAccountId(String accountId);

    int addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException;

    Consumer getConsumerByID(long id);

    List<Consumer> getAllUser();

    boolean removeById(long id);

    int updateConsumerEmail(String email, String accountId) throws ConsumerException;

    int updateConsumerNickname(String nickname, String accountId) throws ConsumerException;

    boolean updateConsumerPicture(String portrait, String accountId) throws ConsumerException;

}
