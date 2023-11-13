package com.noint.shelterzoo.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private int code;
    private String message;

    public GeneralException(ExceptionEnum e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }
}
