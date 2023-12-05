package com.noint.shelterzoo.domain.support.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupportPetListResponseVO {
    private Long supportPetSeq;
    private Long petSeq;
    private BigDecimal requiredMoney;
    private BigDecimal supportedMoney;
    private String disease;
    private String createdAt;
    private String kind;
    private String kindDetail;
    private String birth;
    private String thumbnail;
}
