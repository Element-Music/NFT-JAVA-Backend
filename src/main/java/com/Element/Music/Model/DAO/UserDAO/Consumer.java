package com.Element.Music.Model.DAO.UserDAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "consumer")
@AllArgsConstructor
public class Consumer extends User implements Serializable {

    private static final long serialVersionUID = -2214230518390003400L;

}
