package com.Element.Music.Model.DAO.TradeDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity(name = "Price")
@Table(name = "Price")
@AllArgsConstructor
@NoArgsConstructor
public class Price extends BaseEntity {

    private Double originalPrice;

    private Double rate;//平台收取的比例

    private Double showPrice;//含义为最终展示的价格，没有做discount之前

    public Double getShowPrice() {
        this.showPrice = originalPrice * (1 + rate);
        return showPrice;
    }
}
