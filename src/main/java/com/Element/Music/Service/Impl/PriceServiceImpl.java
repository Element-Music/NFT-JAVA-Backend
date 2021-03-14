package com.Element.Music.Service.Impl;

import com.Element.Music.Exception.SongException;
import com.Element.Music.Model.DAO.TradeDAO.Price;
import com.Element.Music.Repository.TradeRepository.PriceRepository;
import com.Element.Music.Service.PriceService;
import com.Element.Music.Service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final SongService songService;

    public PriceServiceImpl(PriceRepository priceRepository, SongService songService) {
        this.priceRepository = priceRepository;
        this.songService = songService;
    }

    @Override
    public Price initializePrice(Long songId, Double originalPrice, Double rate)  {
        Price price = new Price();
        price.setSongId(songId);
        price.setOriginalPrice(originalPrice);
        price.setRate(rate);
        Price initializeRes = priceRepository.save(price);
        if(initializeRes != null){
            songService.updateSongPrice(songService.getSongById(songId), initializeRes.getShowPrice());
        }
        return initializeRes;
    }

    @Override
    public Price getPriceById(Long songId){
        return priceRepository.findBySongIdAndDeletedIsFalse(songId);
    }


    @Override
    public Double getDisplayPriceById(Long songId){
        Price returnPrice = priceRepository.findBySongIdAndDeletedIsFalse(songId);
        if(returnPrice == null){
            return -1.0;
        }
        return returnPrice.getShowPrice();
    }

    @Override
    public List<Price> getAllPrice(){
        return priceRepository.findAll();
    }

} 
