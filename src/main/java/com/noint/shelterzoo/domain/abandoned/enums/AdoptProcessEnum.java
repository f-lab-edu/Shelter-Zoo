package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AdoptProcessEnum {
    RESERVATION("예약"),
    CANCEL("취소"),
    ADOPTED("입양"),
    ;
    private final String stateStr;

    public static AdoptProcessEnum findEnumByStateStr(String state) {
        return Arrays.stream(AdoptProcessEnum.values())
                .filter(e -> state.equals(e.getStateStr()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionEnum.UNKNOWN_ADOPT_PROCESS));
    }
}
