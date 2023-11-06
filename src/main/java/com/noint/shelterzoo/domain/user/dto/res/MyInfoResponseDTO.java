package com.noint.shelterzoo.domain.user.dto.res;

import com.noint.shelterzoo.domain.user.vo.res.MyInfoResponseVO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyInfoResponseDTO {
    private Long seq;
    private String email;
    private String nickname;
    private BigDecimal money;
    private String state;
    private String createdAt;

    public static MyInfoResponseDTO create(MyInfoResponseVO vo){
        MyInfoResponseDTO dto = new MyInfoResponseDTO();
        dto.setSeq(vo.getSeq());
        dto.setEmail(vo.getEmail());
        dto.setNickname(vo.getNickname());
        dto.setMoney(vo.getMoney());
        dto.setState(vo.getState());
        dto.setCreatedAt(vo.getCreatedAt());

        return dto;
    }
}
