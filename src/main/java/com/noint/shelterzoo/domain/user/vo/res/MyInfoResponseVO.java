package com.noint.shelterzoo.domain.user.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MyInfoResponseVO {
    private Long seq;
    private String email;
    private String nickname;
    private BigDecimal money;
    private String state;
    private String createdAt;
}
