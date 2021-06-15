package com.Element.Music.Model.DTO.UserDTO;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ConsumerDTO {
    private static final long serialVersionUID = -2214230518390003400L;

    private String nickName;

}
