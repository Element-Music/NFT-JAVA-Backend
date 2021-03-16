package com.Element.Music.Model.DAO.TradeDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity(name = "Purse")
@Table(name = "purse")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Purse extends BaseEntity{

    private Long consumerId;

    private Double balance;

} 
