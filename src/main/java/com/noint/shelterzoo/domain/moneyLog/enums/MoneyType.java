package com.noint.shelterzoo.domain.moneyLog.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyType {
    DEPOSIT("입금"),
    WITHDRAWAL("출금"),
    ;

    private final String typeStr;
}
