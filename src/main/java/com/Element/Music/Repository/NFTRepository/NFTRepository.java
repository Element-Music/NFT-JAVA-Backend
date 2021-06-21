package com.Element.Music.Repository.NFTRepository;

import com.Element.Music.Model.DAO.NFTDAO.NFT;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NFTRepository extends JpaRepository<NFT, Long> {
    Optional<NFT> findByTokenIdAndDeletedIsFalse(long tokenId);
}