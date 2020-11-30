package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.UserRepository.ConsumerRepository;
import com.Element.Music.Service.ConsumerService;
import com.Element.Music.Util.PaternUtil;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

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
    public void delete(long consumerId) {
        consumerRepository.deleteById(consumerId);
    }

    @Override
    public Consumer addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (consumer.getPassWord() == null || !PaternUtil.isUserName(consumer.getName()) || !PaternUtil.isMobile(consumer.getPhoneNum())) {
            if (consumer.getPassWord() == null) {
                throw new ConsumerException("absence of password");
            } else if (!PaternUtil.isMobile(consumer.getPhoneNum())) {
                throw new ConsumerException("phoneNumber is illegal");
            } else {
                throw new ConsumerException("userName is illegal");
            }
        }
        String pwd = consumer.getPassWord();
        MessageDigest MD5 = MessageDigest.getInstance("MD5");
        consumer.setPassWord(String.valueOf(MD5.digest(pwd.getBytes("utf-8"))));
        return consumerRepository.save(consumer);
    }

    @Override
    @Deprecated
    public boolean verifyPasswdByUserName(String userName, String passWord) {
        return PaternUtil.isUserName(userName) && consumerRepository.findByNameAndPassWord(userName, passWord).get() != null;
    }

    @Override
    public boolean verifyPasswdByPhoneNum(String phoneNum, String passWord) {
        return PaternUtil.isMobile(phoneNum) && consumerRepository.findByPhoneNumAndPassWord(phoneNum, passWord).get() != null;
    }

    @Override
    public boolean verifyPasswdByEmail(String Email, String passWord) {
        return PaternUtil.isUserName(Email) && consumerRepository.findByNameAndPassWord(Email, passWord).get() != null;
    }

    @Override
    public Consumer updateConsumer(Consumer consumer) {
        if (consumer == null) {//判空操作一大堆

        }
        return consumerRepository.save(consumer);
    }

    @Override
    public boolean updateUserPicture(Consumer consumer) throws ConsumerException {
        if (consumer.getId() == null || consumer.getPortrait() == null) {
            if (consumer.getId() == null)
                throw new ConsumerException("更新头像缺失ID");
            if (consumer.getPortrait() == null)
                throw new ConsumerException("更新头像缺失路径");
        }
        Consumer consumer1 = consumerRepository.findById(consumer.getId()).get();
        consumer1.setPortrait(consumer.getPortrait());
        consumerRepository.save(consumer1);
        return true;
    }

    @Override
    public Consumer getConsumerByID(long id) {
        return consumerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Consumer> getAllUser() {
        return consumerRepository.findAll();
    }

    @Override
    public boolean removeById(long id) {
        Optional<Consumer> consumerOptional = consumerRepository.findById(id);
        if (consumerOptional.get() != null) {
            Consumer consumer = consumerOptional.get();
            consumer.setDeleted(true);
            consumerRepository.save(consumer);
            return true;
        }
        return false;
    }


}