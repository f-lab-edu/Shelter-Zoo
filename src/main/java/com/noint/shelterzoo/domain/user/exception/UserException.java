package com.noint.shelterzoo.domain.user.exception;

import com.noint.shelterzoo.domain.user.enums.UserExceptionBody;
import com.noint.shelterzoo.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(UserExceptionBody e) {
        super(e);
    }
}
