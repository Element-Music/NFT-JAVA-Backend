package com.Element.Music.Model.DAO.TradeDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity(name = "Price")
@Table(name = "price")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Price extends BaseEntity {

    @OneToOne
    private Song song;

    private Double originalPrice;

    private Double rate;//平台收取的比例

    private Double showPrice;//含义为最终展示的价格，没有做discount之前

    public Double getShowPrice() {
        this.showPrice = originalPrice * (1 + rate);
        return showPrice;
    }

    @Override
    public String toString() {
        return showPrice.toString();
    }
}
