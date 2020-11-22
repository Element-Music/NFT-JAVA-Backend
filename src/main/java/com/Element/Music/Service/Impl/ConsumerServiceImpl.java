package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.UserRepository.ConsumerRepository;
import com.Element.Music.Service.ConsumerService;
import com.Element.Music.Util.PaternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {


    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
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
        return PaternUtil.isUserName(consumer.getName()) && PaternUtil.isMobile(consumer.getPhoneNum())
                ? consumerRepository.save(consumer) : null;
    }

    @Override
    public boolean verifyPasswdByUserName(String userName, String passWord) {
        return PaternUtil.isUserName(userName) && consumerRepository.findByNameAndPassWord(userName, passWord).get() != null;
    }

    @Override
    public boolean verifyPasswdByPhoneNum(String phoneNum, String passWord) {
        return PaternUtil.isMobile(phoneNum) && consumerRepository.findByPhoneNumAndPassWord(phoneNum, passWord).get() != null;
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        return consumerRepository.save(consumer);
    }

    @Override
    public boolean updateUserPicture(Consumer consumer) {
        return false;
    }

    @Override
    public Consumer getConsumerByID(long id) {
        return consumerRepository.findById(id).get();
    }

    @Override
    public List<Consumer> getAllUser() {
        return consumerRepository.findAll();
    }

    @Override
    public boolean removeById(long id) {
        consumerRepository.deleteById(id);
        return consumerRepository.findById(id).get() == null ? true : false;
    }


}
