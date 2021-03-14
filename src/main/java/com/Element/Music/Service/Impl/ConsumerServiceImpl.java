package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.ConsumerException;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.UserRepository.ConsumerRepository;
import com.Element.Music.Repository.MusicRepository.SongRepository;
import com.Element.Music.Service.PurseService;
import com.Element.Music.Service.ConsumerService;
import com.Element.Music.Service.SongService;
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

    private final SongService songService;

    private final PurseService purseService;

    private final MessageDigest MD5 = MessageDigest.getInstance("MD5");

    public ConsumerServiceImpl(ConsumerRepository consumerRepository, SongService songService, PurseService purseService) throws NoSuchAlgorithmException {

        this.consumerRepository = consumerRepository;

        this.songService = songService;

        this.purseService = purseService;
    }

    @Override
    public Consumer logout() {
        return null;
    }

    @Override
    public void delete(long consumerId) {
        consumerRepository.deleteById(consumerId);
    }

//    @Override
//    public Consumer addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
//        if (consumer.getPassWord() == null || !PaternUtil.isUserName(consumer.getName()) || !PaternUtil.isMobile(consumer.getPhoneNum())) {
//            if (consumer.getPassWord() == null) {
//                throw new ConsumerException("absence of password");
//            } else if (!PaternUtil.isMobile(consumer.getPhoneNum())) {
//                throw new ConsumerException("phoneNumber is illegal");
//            } else {
//                throw new ConsumerException("userName is illegal");
//            }
//        }
//        String pwd = consumer.getPassWord();
//        consumer.setPassWord(DigestUtils.md5DigestAsHex(pwd.getBytes()));
//        return consumerRepository.save(consumer);
//    }

    @Override
    public Consumer addConsumer(Consumer consumer) throws ConsumerException, NoSuchAlgorithmException, UnsupportedEncodingException {
        if (consumer.getPhoneNum() == null && consumer.getEmail() == null) {
            throw new ConsumerException("absence of email and phoneNum");
        } else if (consumerRepository.findByPhoneNum(consumer.getPhoneNum()) != null ||
                consumerRepository.findByEmail(consumer.getEmail()) != null) {
            return null;
        } else if (consumer.getPassWord() == null || !PaternUtil.isUserName(consumer.getName()) ||
                (consumer.getPhoneNum() == "" && !PaternUtil.isMobile(consumer.getPhoneNum())) ||
                (consumer.getEmail() == "" && !PaternUtil.isEmail(consumer.getEmail()))) {
            if (consumer.getPassWord() == null) {
                throw new ConsumerException("absence of password");
            } else if (consumer.getPhoneNum() == "" && !PaternUtil.isMobile(consumer.getPhoneNum())) {
                throw new ConsumerException("phoneNumber is illegal");
            } else if (consumer.getEmail() == "" && !PaternUtil.isEmail(consumer.getEmail())) {
                throw new ConsumerException("email is illegal");
            }else {
                throw new ConsumerException("userName is illegal");
            }
        }
        String pwd = consumer.getPassWord();
        consumer.setPassWord(DigestUtils.md5DigestAsHex(pwd.getBytes()));
        Consumer returnConsumer = consumerRepository.save(consumer);
        if(returnConsumer != null){
            Long consumerId = returnConsumer.getId();
            purseService.initializePurse(consumerId);
        }
        return returnConsumer;
    }

    @Override
    public void addToCollection(long consumerId, long songId) {
        Song song = songService.getSongById(songId);
        Optional<Consumer> optionalConsumer = consumerRepository.findById(consumerId);
        Consumer consumer = optionalConsumer.get();
        Set<Song> likes = consumer.getCollections();
        likes.add(song);
        consumer.setCollections(likes);
        consumerRepository.save(consumer);
    }

    @Override
    public Set<Song> getCollection(long consumerId) {
//        Optional<Consumer> optionalConsumer = consumerRepository.findById(consumerId);
        Consumer consumer = getConsumerByID(consumerId);
        return consumer.getCollections();
    }


    @Override
    public void addToPaidList(long consumerId, Song song) {
        consumerRepository.getOne(consumerId).getCollections().add(song);
    }

    @Override
    public Set<Song> getPaidList(long consumerId){
        return consumerRepository.getOne(consumerId).getCollections();
    }

    @Override
    @Deprecated
    public int verifyPasswdByUser(String user, String passWord) throws UnsupportedEncodingException {
        if(!PaternUtil.isMobile(user) && !PaternUtil.isEmail(user)){
            return 1;
        }else if (consumerRepository.findByEmail(user) == null && consumerRepository.findByPhoneNum(user) == null) {
            return 2;
        }else if(PaternUtil.isMobile(user) && consumerRepository.
                findByPhoneNumAndPassWord(user, DigestUtils.md5DigestAsHex(passWord.getBytes())).orElse(null) == null){
            return 3;
        }else if(PaternUtil.isEmail(user) && consumerRepository.
                findByEmailAndPassWord(user, DigestUtils.md5DigestAsHex(passWord.getBytes())).orElse(null) == null){
            return 3;
        }else {
            return 0;
        }
    }

//    @Override
//    @Deprecated
//    public int verifyPasswdByUserName(String userName, String passWord) throws UnsupportedEncodingException {
//        return PaternUtil.isUserName(userName) && consumerRepository.
//                findByNameAndPassWord(userName, DigestUtils.md5DigestAsHex(passWord.getBytes())).orElse(null) != null;
//    }

//    @Override
//    public boolean verifyPasswdByPhoneNum(String phoneNum, String passWord) throws UnsupportedEncodingException {
//        return PaternUtil.isMobile(phoneNum) && consumerRepository.
//                findByPhoneNumAndPassWord(phoneNum, DigestUtils.md5DigestAsHex(passWord.getBytes())).orElse(null) != null;
//    }
//
//    @Override
//    public boolean verifyPasswdByEmail(String Email, String passWord) throws UnsupportedEncodingException {
//        return PaternUtil.isEmail(Email) && consumerRepository.
//                findByEmailAndPassWord(Email, DigestUtils.md5DigestAsHex(passWord.getBytes())).orElse(null) != null;
//    }

    @Override
    public boolean updateConsumer(Consumer consumer) throws ConsumerException{
        if(consumer == null)
            throw new ConsumerException("更改用户接口缺失consumer");
        Optional<Consumer> consumerOptional = consumerRepository.findById(consumer.getId());
        if (consumerOptional.get() != null || !consumerOptional.get().isDeleted()) {
            Consumer consumer1 = consumerOptional.orElse(null);
            consumer1.setId(consumer.getId());
            consumer1.setName(consumer.getName());
            consumer1.setPassWord(consumer.getPassWord());
            consumer1.setSex(consumer.getSex());
            consumer1.setPhoneNum(consumer.getPhoneNum());
            consumer1.setEmail(consumer.getEmail());
            consumer1.setBirth(consumer.getBirth());
            consumer1.setLocation(consumer.getLocation());
            consumer1.setPortrait(consumer.getPortrait());
            consumerRepository.save(consumer1);
            return true;
        }
        return false;
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