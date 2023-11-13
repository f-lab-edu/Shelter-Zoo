package com.noint.shelterzoo.domain.abandoned.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum AdoptCancelPayBackPenaltyEnum {
    DAY5(BigDecimal.valueOf(80)),
    DAY3(BigDecimal.valueOf(50)),
    DAY1(BigDecimal.valueOf(20)),
    ;

    private final BigDecimal percent;

    public static BigDecimal payBack(BigDecimal reservation, AdoptCancelPayBackPenaltyEnum penaltyEnum) {
        return reservation.multiply(penaltyEnum.percent.divide(BigDecimal.valueOf(100)));
    }
}
