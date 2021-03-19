package com.Element.Music.Service.Impl;

import com.Element.Music.Repository.TradeRepository.OrderRepository;
import com.Element.Music.Model.DAO.TradeDAO.*;
import com.Element.Music.Model.DAO.MusicDAO.Song;
import com.Element.Music.Service.*;
import org.springframework.stereotype.Service;
import com.Element.Music.IdProducer.OrderId;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final PriceService priceService;

    private final PurseService purseService;

    private final SongService songService;


    public OrderServiceImpl(OrderRepository orderRepository, PriceService priceService, PurseService purseService, SongService songService) throws NoSuchAlgorithmException {

        this.orderRepository = orderRepository;

        this.priceService = priceService;

        this.purseService = purseService;

        this.songService = songService;
    }

    @Override
    public OrderId generateOrderId (){
        Random rd = new Random();
        long workerId = rd.nextInt(31), datacenterId = rd.nextInt(31); //工作id，数据中心ID

        //获取当前ip,生成工作id
        String ip = "47.97.121.189";
        if(ip != null) {
            workerId = Long.parseLong(ip.replaceAll("\\.", ""));
            workerId = workerId % 32; //因为占用5位，模32
        }
        OrderId orderId = new OrderId(workerId, datacenterId);
        return orderId;
    }

    @Override
    public int addNewOrder(Long songId, Long consumerId) {

        Purse consumerPurse = purseService.getPurseById(consumerId);
        Price songPrice = priceService.getPriceById(songId);
        if (songPrice.getShowPrice() > consumerPurse.getBalance()) {
            return -1;
        } //Error Code -1: 余额不够
        OrderId orderId = generateOrderId();

        ConsumerOrder order = new ConsumerOrder();
        order.setConsumerId(consumerId);
        order.setDiscount(0.0);
        order.setPayPrice(songPrice.getShowPrice());
        order.setPriceId(songPrice.getId());
        order.setSongId(songPrice.getSongId());
        order.setOrderCode(orderId.nextId());
        ConsumerOrder returnOrder = orderRepository.save(order);
        if(returnOrder == null){
            return -2; //Error Code: -2: 保存订单失败
        }
        purseService.withdrawBalanceById(consumerId, songPrice.getShowPrice());
        return 1; //新增订单成功
    }


    @Override
    public List<ConsumerOrder> getOrderByConsumerId(Long consumerId){
        return orderRepository.findAllByConsumerIdAndDeletedIsFalse(consumerId);
    }

    @Override
    public List<Song> getSongIdByConsumerId(Long consumerId){
        List<ConsumerOrder> orderRes = orderRepository.findAllByConsumerIdAndDeletedIsFalse(consumerId);
        List<Song> returnSong = new ArrayList<>();

        for(ConsumerOrder consumerOrder: orderRes){
            returnSong.add(songService.getSongById(consumerOrder.getSongId()));
        }

        return returnSong;
    }

    public List<ConsumerOrder> getAllOrder(){
        return orderRepository.findAll();
    }
}
