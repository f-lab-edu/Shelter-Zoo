package com.noint.shelterzoo.domain.support.exception;

import com.noint.shelterzoo.domain.support.enums.SupportExceptionBody;
import com.noint.shelterzoo.exception.GeneralException;

public class SupportException extends GeneralException {
    public SupportException(SupportExceptionBody e) {
        super(e);
    }
}
