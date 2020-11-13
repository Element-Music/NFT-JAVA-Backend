package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.UserRepository.ConsumerRepository;
import com.Element.Music.Service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {


    private ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Override
    public Consumer register() {
        return null;
    }

    @Override
    public Consumer login() {
        return null;
    }

    @Override
    public Consumer logout() {
        return null;
    }

    @Override
    public Consumer delete(long ConsumerId) {
        return null;
    }

    @Override
    public Consumer update(Consumer consumer) {
        return null;
    }

    @Override
    public Consumer addConsumer(Consumer consumer) {
        return null;
    }

    @Override
    public boolean verifyPasswd(String userName, String passWord) {
        return consumerRepository.findByNameAndPassWord(userName, passWord).get() != null;
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        return null;
    }

    @Override
    public boolean updateUserPicture(Consumer consumer) {
        return false;
    }
}
