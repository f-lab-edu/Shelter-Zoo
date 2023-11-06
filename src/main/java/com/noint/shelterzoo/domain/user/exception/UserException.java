package com.noint.shelterzoo.domain.user.exception;

import com.noint.shelterzoo.domain.user.enums.UserExceptionEnum;
import com.noint.shelterzoo.exception.GeneralException;

public class UserException extends GeneralException {
    public UserException(UserExceptionEnum e){
        super(e);
    }
}
