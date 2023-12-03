package com.noint.shelterzoo.domain.moneyLog.enums;

import com.noint.shelterzoo.domain.moneyLog.exception.MoneyLogException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MoneyUpdatePurpose {
    ADOPT_RESERVATION("예약보증"),
    ADOPT_PAYBACK("보증반환"),
    CHARGE("충전"),
    SUPPORT("후원"),
    ;

    private final String purposeStr;

    public static MoneyUpdatePurpose findEnumByStr(String purposeStr) {
        return Arrays.stream(MoneyUpdatePurpose.values())
                .filter(e -> purposeStr.equals(e.getPurposeStr()))
                .findAny()
                .orElseThrow(() -> new MoneyLogException(MoneyLogExceptionEnum.UNKNOWN_LOG_TYPE));
    }
}
