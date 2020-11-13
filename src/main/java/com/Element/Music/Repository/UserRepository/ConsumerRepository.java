package com.Element.Music.Repository.UserRepository;

import com.Element.Music.Model.DAO.UserDAO.Consumer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
    Optional<Consumer> findByNameAndPassWord(String name, String password);
}
