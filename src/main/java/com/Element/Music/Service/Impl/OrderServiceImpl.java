package com.Element.Music.Service.Impl;

import com.Element.Music.Service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl {
    @Autowired
    private final TradeService tradeService;

    public OrderServiceImpl(TradeService tradeService) {
        this.tradeService = tradeService;
    }


}
