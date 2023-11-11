package com.noint.shelterzoo.domain.abandoned.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdoptProcessEnum {
    RESERVATION("예약"),
    CANCEL("취소"),
    ADOPTED("입양")
    ;
    private final String stateStr;
}
