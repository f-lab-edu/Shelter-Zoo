package com.noint.shelterzoo.domain.moneyLog.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyLogDetailWithAdoptResVO {
    private Long moneyLogSeq;
    private Long userSeq;
    private String moneyType;
    private BigDecimal money;
    private BigDecimal totalMoney;
    private String type;
    private String createdAt;
    private String adoptState;
    private Long petSeq;
    private String petThumbnail;
}
