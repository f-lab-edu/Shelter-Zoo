package com.noint.shelterzoo.domain.charge.exception;

import com.noint.shelterzoo.domain.charge.enums.ChargeExceptionEnum;
import com.noint.shelterzoo.exception.GeneralException;

public class ChargeException extends GeneralException {
    public ChargeException(ChargeExceptionEnum e) {
        super(e);
    }
}
