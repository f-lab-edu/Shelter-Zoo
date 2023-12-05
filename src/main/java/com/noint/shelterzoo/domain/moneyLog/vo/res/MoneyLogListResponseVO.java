package com.noint.shelterzoo.domain.moneyLog.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogListResponseVO {
    private Long moneyLogSeq;
    private Long userSeq;
    private String moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private String type;
    private Long adoptSeq;
    private Long supportLogSeq;
    private Long chargeLogSeq;
    private String createAt;
}
