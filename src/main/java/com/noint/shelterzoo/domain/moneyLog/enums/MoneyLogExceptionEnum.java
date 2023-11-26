package com.noint.shelterzoo.domain.moneyLog.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyLogExceptionEnum implements ExceptionEnum {
    UNKNOWN_LOG_TYPE(400, "알 수 없는 로그 타입"),
    ;

    private final int code;
    private final String message;
}
