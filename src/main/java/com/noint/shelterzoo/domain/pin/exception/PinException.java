package com.noint.shelterzoo.domain.pin.exception;

import com.noint.shelterzoo.domain.pin.enums.PinExceptionEnum;
import com.noint.shelterzoo.exception.GeneralException;

public class PinException extends GeneralException {
    public PinException(PinExceptionEnum e) {
        super(e);
    }
}
