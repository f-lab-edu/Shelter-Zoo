package com.noint.shelterzoo.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyUpdatePurposeEnum {
    ADOPT_RESERVATION("에약보증"),
    ADOPT_PAYBACK("보증반환"),
    CHARGE("충전"),
    SUPPORT("후원"),
    ;

    private final String purposeStr;
}
