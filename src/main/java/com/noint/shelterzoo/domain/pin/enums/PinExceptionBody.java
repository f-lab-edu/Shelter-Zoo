package com.noint.shelterzoo.domain.pin.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PinExceptionBody implements ExceptionEnum {
    DUPLICATED_PIN(400, "이미 관심 동물로 등록됨"),
    ;

    private final int code;
    private final String message;
}
