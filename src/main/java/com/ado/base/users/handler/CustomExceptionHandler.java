package com.ado.base.users.handler;

import com.ado.base.users.api.response.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(errors)
                .build();
        return new ResponseEntity<>(
                errorMessageDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(Collections.singletonList(getMessage(ex.getMessage())))
                .build();
        return new ResponseEntity<>(
            errorMessageDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private String getMessage(String exceptionMessage) {
        log.info("exceptionMessage={}",exceptionMessage);
        String message = "dafault exception message";
        if(exceptionMessage.contains("PUBLIC.") && exceptionMessage.contains("_INDEX_2")) {
            int beginIndex = exceptionMessage.indexOf("PUBLIC.") +  "PUBLIC.".length();
            int finalIndex = exceptionMessage.indexOf("_INDEX_2");
            log.info("beginIndex={}, finalIndex={}",beginIndex, finalIndex);
            return exceptionMessage.subSequence(beginIndex , finalIndex).toString();
        }
        return message;
    }

}
