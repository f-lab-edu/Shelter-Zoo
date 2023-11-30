package com.noint.shelterzoo.domain.user.vo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyUpdateRequestVO {
    private Long userSeq;
    private BigDecimal totalMoney;

    public static MoneyUpdateRequestVO create(Long userSeq, BigDecimal totalMoney) {
        MoneyUpdateRequestVO vo = new MoneyUpdateRequestVO();
        vo.setTotalMoney(totalMoney);
        vo.setUserSeq(userSeq);

        return vo;
    }
}
