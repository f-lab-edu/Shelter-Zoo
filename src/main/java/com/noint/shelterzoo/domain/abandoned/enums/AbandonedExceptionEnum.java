package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbandonedExceptionEnum implements ExceptionEnum {
    UNKNOWN_GENDER_ID(404, "알 수 없는 성별 식별자"),
    UNKNOWN_NEUTER_ID(404, "알 수 없는 중성화 식별자"),
    NO_CONTENT(404, "No Content"),
    ;

    private final int code;
    private final String message;
}
