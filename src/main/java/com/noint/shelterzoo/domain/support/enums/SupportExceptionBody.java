package com.noint.shelterzoo.domain.support.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SupportExceptionBody implements ExceptionEnum {
    DISABLED_DONATE_BY_GOAL(400, "후원 금액 달성"),
    OVER_REMAINING_AMOUNT(400, "후원 금액 초과"),
    RACK_OF_MONEY(400, "재화 부족"),
    ;

    private final int code;
    private final String message;
}
