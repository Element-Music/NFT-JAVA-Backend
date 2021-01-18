package com.Element.Music.Model.DAO.TradeDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Model.DAO.UserDAO.Consumer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "Order")
@Data
@Builder
@Table(name = "Order")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @OneToOne
    private Song song;

    @OneToOne
    private Consumer consumer;

    private Long orderCode;

    private Double payPrice;//实付金额，考虑一下重新取名字

    private Double discount;//优惠金额，就是原价-实付金额

}
