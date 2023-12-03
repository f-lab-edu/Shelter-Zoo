package com.noint.shelterzoo.domain.moneyLog.vo.req;

import lombok.Data;

@Data
public class MoneyLogDetailRequestVO {
    private Long userSeq;
    private Long moneyLogSeq;

    public static MoneyLogDetailRequestVO create(Long userSeq, Long moneyLogSeq) {
        MoneyLogDetailRequestVO vo = new MoneyLogDetailRequestVO();
        vo.setUserSeq(userSeq);
        vo.setMoneyLogSeq(moneyLogSeq);

        return vo;
    }
}
