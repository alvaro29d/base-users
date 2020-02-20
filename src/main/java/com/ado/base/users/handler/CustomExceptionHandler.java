package com.ado.base.users.handler;

import com.ado.base.users.api.response.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.CommunicationException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ado.base.users.model.User.UNIQUE_USER_EMAIL;
import static com.ado.base.users.model.User.UNIQUE_USER_NAME;

@ControllerAdvice
@Slf4j
/**
 * https://www.toptal.com/java/spring-boot-rest-api-error-handling
 */
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

    @ExceptionHandler({ JDBCConnectionException.class})
    public ResponseEntity<Object> handleJDBCConnectionException(
            JDBCConnectionException ex, WebRequest request) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .messages(Collections.singletonList("There was an unexpected error."))
                .build();
        log.error("Error connecting to database, error={}", ex.getMessage(), ex);
        return new ResponseEntity<>(
                errorMessageDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        ErrorMessageDTO errorMessageDTO = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .statusDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .messages(Collections.singletonList(getMessage(ex.getMessage().toLowerCase())))
                .build();
        return new ResponseEntity<>(
            errorMessageDTO, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private String getMessage(String exceptionMessage) {
        log.debug("exceptionMessage={}",exceptionMessage);
        String message = "default exception message";
        if(exceptionMessage.contains(UNIQUE_USER_EMAIL)) {
            message = UNIQUE_USER_EMAIL;
        }else if(exceptionMessage.contains(UNIQUE_USER_NAME)) {
            message = UNIQUE_USER_NAME;
        } else {
            log.error("Error Message not handled, exceptionMessage=\"{}\"", exceptionMessage);
        }
        return message;
    }

}
