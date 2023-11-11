package com.noint.shelterzoo.domain.abandoned.exception;

import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionEnum;
import com.noint.shelterzoo.exception.GeneralException;

public class AbandonedException extends GeneralException {
    public AbandonedException(AbandonedExceptionEnum e) {
        super(e);
    }
}
