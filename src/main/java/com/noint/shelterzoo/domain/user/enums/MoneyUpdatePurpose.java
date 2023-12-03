package com.noint.shelterzoo.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyUpdatePurpose {
    ADOPT_RESERVATION("예약보증"),
    ADOPT_PAYBACK("보증반환"),
    CHARGE("충전"),
    SUPPORT("후원"),
    ;

    private final String purposeStr;
}
