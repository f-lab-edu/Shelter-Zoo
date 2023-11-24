package com.noint.shelterzoo.domain.abandoned.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public enum AdoptCancelPayBackPenaltyEnum {
    DAY5(BigDecimal.valueOf(0.8)),
    DAY3(BigDecimal.valueOf(0.5)),
    DAY1(BigDecimal.valueOf(0.2)),
    ;

    private final BigDecimal percent;

    public static BigDecimal payBackWithPenalty(BigDecimal reservation, AdoptCancelPayBackPenaltyEnum penaltyEnum) {
        return reservation.multiply(penaltyEnum.percent);
    }
}
