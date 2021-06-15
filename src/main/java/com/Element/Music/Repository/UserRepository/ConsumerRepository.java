package com.Element.Music.Repository.UserRepository;

import com.Element.Music.Model.DAO.UserDAO.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
//    Optional<Consumer> findByNameAndPassWord(String name, String password);

    Consumer findByNickname(String Nickname);

    Consumer findByEmail(String Email);

    Optional<Consumer> findByAccountId(String AccountId);

    Consumer findByIdAndDeletedIsFalse(Long Id);
}
