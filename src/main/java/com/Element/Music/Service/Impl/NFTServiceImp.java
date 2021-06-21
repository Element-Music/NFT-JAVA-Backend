package com.Element.Music.Service.Impl;

import com.Element.Music.Model.DAO.NFTDAO.NFT;
import com.Element.Music.Repository.NFTRepository.NFTRepository;
import com.Element.Music.Service.NFTService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class NFTServiceImp implements NFTService{

    private final NFTRepository nftRepository;

    public NFTServiceImp(NFTRepository nftRepository) {
        this.nftRepository = nftRepository;
    }

    @Override
    public int initializePrice(Long tokenId, Double originalPrice, Double rate){
        Optional<NFT> nftOptional = nftRepository.findByTokenIdAndDeletedIsFalse(tokenId);
        if (!nftOptional.isEmpty()) return 1;
        NFT nft = new NFT();
        nft.setTokenId(tokenId);
        nft.setPrice(originalPrice);
        nft.setRate(rate);
        nftRepository.save(nft);
        return 0;
    }

    @Override
    public NFT getNFTByTokenId(Long tokenId){
        Optional<NFT> nftOptional = nftRepository.findByTokenIdAndDeletedIsFalse(tokenId);
        if (nftOptional.isEmpty()) return null;
        if (!nftOptional.get().isDeleted()) {
            NFT nft1 = nftOptional.get();
            return nft1;
        }
        return null;
    }

    @Override
    public int updateNFTPrice(Long tokenId, Double updatedPrice){
        Optional<NFT> nftOptional = nftRepository.findByTokenIdAndDeletedIsFalse(tokenId);
        if (nftOptional.isEmpty()) return 1;
        if (!nftOptional.get().isDeleted()) {
            NFT nft1 = nftOptional.get();
            nft1.setPrice(updatedPrice);
            nftRepository.save(nft1);
            return 0;
        }
        return 2;
    }

    @Override
    public List<NFT> getAllNFTPrice(){
        return nftRepository.findAll();
    }

} 
