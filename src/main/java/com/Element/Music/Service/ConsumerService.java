package com.Element.Music.Service;

import com.Element.Music.Model.DAO.UserDAO.Consumer;

public interface ConsumerService {
    Consumer register();

    Consumer login();

    Consumer logout();

    Consumer delete(long ConsumerId);

    Consumer update(Consumer consumer);

    Consumer addConsumer(Consumer consumer);

    boolean verifyPasswd(String userName, String passWord);

    Consumer updateConsumer(Consumer consumer);

    boolean updateUserPicture(Consumer consumer);
}
