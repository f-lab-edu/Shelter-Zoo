package com.noint.shelterzoo.domain.moneyLog.exception;

import com.noint.shelterzoo.domain.moneyLog.enums.MoneyLogExceptionEnum;
import com.noint.shelterzoo.exception.GeneralException;

public class MoneyLogException extends GeneralException {
    public MoneyLogException(MoneyLogExceptionEnum e) {
        super(e);
    }
}
