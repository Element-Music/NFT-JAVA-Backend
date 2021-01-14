package com.Element.Music.Repository.TradeRepository;

import com.Element.Music.Model.DAO.TradeDAO.Order;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
