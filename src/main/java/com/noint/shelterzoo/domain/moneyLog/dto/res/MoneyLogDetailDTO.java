package com.noint.shelterzoo.domain.moneyLog.dto.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogDetailDTO {
    private Long moneyLogSeq;
    private Long userSeq;
    private String moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private String type;
    private String createdAt;
}
