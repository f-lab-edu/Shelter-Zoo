package com.noint.shelterzoo.domain.abandoned.exception;

import com.noint.shelterzoo.domain.abandoned.enums.AbandonedExceptionBody;
import com.noint.shelterzoo.exception.GeneralException;

public class AbandonedException extends GeneralException {
    public AbandonedException(AbandonedExceptionBody e) {
        super(e);
    }
}
