package com.noint.shelterzoo.domain.pin.exception;

import com.noint.shelterzoo.domain.pin.enums.PinExceptionBody;
import com.noint.shelterzoo.exception.GeneralException;

public class PinException extends GeneralException {
    public PinException(PinExceptionBody e) {
        super(e);
    }
}
