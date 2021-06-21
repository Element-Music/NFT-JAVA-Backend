package com.Element.Music.Model.DAO.NFTDAO;

import com.Element.Music.Model.DAO.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "Price")
@AllArgsConstructor
@NoArgsConstructor

public class NFT extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2214230518390003400L;

    @NonNull
    private long tokenId;
    @NonNull
    private double price;

    private double rate;
}