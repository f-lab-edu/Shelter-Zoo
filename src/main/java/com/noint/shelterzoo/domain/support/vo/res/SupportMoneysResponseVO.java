package com.noint.shelterzoo.domain.support.vo.res;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupportMoneysResponseVO {
    private BigDecimal requiredMoney;
    private BigDecimal supportedMoney;
}
