package com.Element.Music.Service;

import com.Element.Music.Model.DAO.NFTDAO.NFT;
import java.util.List;

public interface NFTService {
    int initializePrice(Long tokenId, Double originalPrice, Double rate);

    NFT getNFTByTokenId(Long tokenId);

    int updateNFTPrice(Long tokenId, Double updatedPrice);

    List<NFT> getAllNFTPrice();
}
