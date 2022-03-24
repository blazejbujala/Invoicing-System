package com.fc.invoicing.exeptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NotFoundExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ItemNotFoundExemption.class)
    public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundExemption e) {
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}
