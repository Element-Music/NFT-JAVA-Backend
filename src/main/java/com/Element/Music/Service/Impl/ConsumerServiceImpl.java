package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.UserRepository.ConsumerRepository;
import com.Element.Music.Service.ConsumerService;
import com.Element.Music.Util.PaternUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    private final MessageDigest MD5 = MessageDigest.getInstance("MD5");

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) throws NoSuchAlgorithmException {
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
    public int addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Optional<Consumer> consumerOptional = consumerRepository.findByAccountId(consumer.getAccountId());
        if (!consumerOptional.isEmpty()) return 1;
        consumerRepository.save(consumer);
        return 0;
    }

    @Override
    public Consumer getConsumerByID(long id) {
        return consumerRepository.findById(id).orElse(null);
    }

    @Override
    public Consumer getConumserByAccountId(String accountId) {
        Optional<Consumer> consumerOptional = consumerRepository.findByAccountId(accountId);
        if (consumerOptional.isEmpty()) return null;
        if (!consumerOptional.get().isDeleted()) {
            Consumer consumer1 = consumerOptional.get();
            return consumer1;
        }
        return null;
    }


    @Override
    public int updateConsumerEmail(String email, String accountId) throws ConsumerException {
        if (email.equals("") || accountId.equals("") )
            throw new ConsumerException("更改用户接口缺失consumer");
        if (consumerRepository.findByEmail(email) != null) {
            return 1;
        }
        Optional<Consumer> consumerOptional = consumerRepository.findByAccountId(accountId);
        if (consumerOptional.isEmpty()) return 2;
        if (!consumerOptional.get().isDeleted()) {
            Consumer consumer1 = consumerOptional.get();
            consumer1.setEmail(email);
            consumerRepository.save(consumer1);
            return 0;
        }
        return 3;
    }

    @Override
    public int updateConsumerNickname(String nickname, String accountId) throws ConsumerException {
        if (nickname.equals("") || accountId.equals("") )
            throw new ConsumerException("更改用户接口缺失consumer");
        if (!nickname.equals("Unnamed") && consumerRepository.findByNickname(nickname) != null) {
            return 1;
        }
        Optional<Consumer> consumerOptional = consumerRepository.findByAccountId(accountId);
        if (consumerOptional.isEmpty()) return 2;
        if (!consumerOptional.get().isDeleted()) {
            Consumer consumer1 = consumerOptional.get();
            consumer1.setNickname(nickname);
            consumerRepository.save(consumer1);
            return 0;
        }
        return 3;
    }

    @Override
    public boolean updateConsumerPicture(String portrait, String accountId) throws ConsumerException {
        if (accountId.equals("") || portrait == null) {
            if (accountId.equals("") ) throw new ConsumerException("更新头像缺失ID");
            else throw new ConsumerException("更新头像缺失路径");
        }
        Optional<Consumer> consumerOptional = consumerRepository.findByAccountId(accountId);
        if (consumerOptional.isEmpty()) return false;
        Consumer consumer1 = consumerOptional.get();
        consumer1.setPortrait(portrait);
        consumerRepository.save(consumer1);
        return true;
    }


    @Override
    public List<Consumer> getAllUser() { return consumerRepository.findAll(); }

    @Override
    public boolean removeById(long id) {
        Optional<Consumer> consumerOptional = consumerRepository.findById(id);
        if (consumerOptional.isEmpty()) return false;
        Consumer consumer = consumerOptional.get();
        consumer.setDeleted(true);
        consumerRepository.save(consumer);
        return true;
    }
}