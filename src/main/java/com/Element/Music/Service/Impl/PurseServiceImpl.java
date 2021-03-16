package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.TradeDAO.Purse;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import com.Element.Music.Repository.TradeRepository.PurseRepository;
import com.Element.Music.Service.PurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurseServiceImpl implements PurseService{
    private final PurseRepository purseRepository;

    public PurseServiceImpl(PurseRepository purseRepository) { this.purseRepository = purseRepository; }

    @Override
    public Purse initializePurse(Long consumer_id) {
        Purse purse = new Purse();
        purse.setConsumerId(consumer_id);
        Double initialBalance = 100.00;
        purse.setBalance(initialBalance);
        return purseRepository.save(purse);
    }

    @Override
    public Purse getPurseById(Long consumer_id){
        return purseRepository.findByConsumerIdAndDeletedIsFalse(consumer_id);
    }


    @Override
    public Double getBalanceByID(Long consumer_id){
        Purse res = purseRepository.findByConsumerIdAndDeletedIsFalse(consumer_id);
        if(res == null){
            return -1.0;
        }
        return res.getBalance();
    }

    @Override
    public Purse addBalanceById(Long consumer_id, Double addValue){
        Purse consumerPurse = purseRepository.findByConsumerIdAndDeletedIsFalse(consumer_id);
        Double newBalance = consumerPurse.getBalance() + addValue;
        consumerPurse.setBalance(newBalance);
        return purseRepository.save(consumerPurse);
    }

    @Override
    public Purse withdrawBalanceById(Long consumer_id, Double withdrawValue){
        Purse consumerPurse = purseRepository.findByConsumerIdAndDeletedIsFalse(consumer_id);
        Double newBalance = consumerPurse.getBalance() - withdrawValue;
        if(newBalance < 0){
            return null;
        }
        consumerPurse.setBalance(newBalance);
        return purseRepository.save(consumerPurse);
    }



}
