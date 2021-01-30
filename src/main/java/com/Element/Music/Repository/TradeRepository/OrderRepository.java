package com.Element.Music.Repository.TradeRepository;

import com.Element.Music.Model.DAO.TradeDAO.ConsumerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ConsumerOrder, Long> {
}
