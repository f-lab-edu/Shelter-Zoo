package com.noint.shelterzoo.domain.abandoned.enums;

import com.noint.shelterzoo.domain.abandoned.exception.AbandonedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AdoptProcess {
    RESERVATION("예약"),
    CANCEL("취소"),
    ADOPTED("입양"),
    ;
    private final String stateStr;

    public static AdoptProcess findEnumByStateStr(String state) {
        return Arrays.stream(AdoptProcess.values())
                .filter(e -> state.equals(e.getStateStr()))
                .findAny()
                .orElseThrow(() -> new AbandonedException(AbandonedExceptionBody.UNKNOWN_ADOPT_PROCESS));
    }
}
