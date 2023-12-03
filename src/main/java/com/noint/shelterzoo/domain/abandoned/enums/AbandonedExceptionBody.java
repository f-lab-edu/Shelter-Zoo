package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.exception.ExceptionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbandonedExceptionBody implements ExceptionEnum {
    UNKNOWN_GENDER_ID(404, "알 수 없는 성별 식별자"),
    UNKNOWN_NEUTER_ID(404, "알 수 없는 중성화 식별자"),
    UNKNOWN_ADOPT_PROCESS(404, "알 수 없는 입양 절차"),
    NO_CONTENT(404, "No Content"),
    NOT_ADOPTABLE(400, "입양 불가능"),
    NOT_UPDATABLE(400, "수정 불가능"),
    LACK_OF_MONEY(400, "재화 부족"),
    UNKNOWN_TYPE(404, "알 수 없는 타입"),
    ;

    private final int code;
    private final String message;
}
