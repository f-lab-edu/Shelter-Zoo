package com.noint.shelterzoo.domain.charge.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChargeExceptionEnum implements ExceptionEnum {
    DUPLICATE_CHARGE_ID(400, "ChargeId 중복"),
    ;

    private final int code;
    private final String message;
}
