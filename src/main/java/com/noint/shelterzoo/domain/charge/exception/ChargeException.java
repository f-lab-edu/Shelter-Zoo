package com.noint.shelterzoo.domain.charge.exception;

import com.noint.shelterzoo.domain.charge.enums.ChargeExceptionBody;
import com.noint.shelterzoo.exception.GeneralException;

public class ChargeException extends GeneralException {
    public ChargeException(ChargeExceptionBody e) {
        super(e);
    }
}
