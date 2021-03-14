package com.Element.Music.Repository.TradeRepository;

import com.Element.Music.Model.DAO.TradeDAO.Purse;

import com.Element.Music.Model.DAO.UserDAO.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurseRepository extends JpaRepository<Purse, Long> {
    Purse findByConsumerIdAndDeletedIsFalse(Long id);
}
