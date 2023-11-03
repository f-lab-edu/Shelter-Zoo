package com.noint.shelterzoo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GeneralException.class})
    public ResponseEntity<Object> exceptionResponse(GeneralException e){
        HashMap<String, Object> body = new HashMap<>();
        body.put("code", e.getCode());
        body.put("message", e.getMessage());
        return ResponseEntity.status(e.getCode()).body(body);
    }
}
