package com.github.rhino10001.filestorage.exception;

import com.github.rhino10001.filestorage.dto.response.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SaveFileException.class)
    protected ResponseEntity<Object> onSaveFileException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_ACCEPTABLE,
                request
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> onNoSuchElementException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                new ExceptionResponse(HttpStatus.NOT_FOUND.value(), "No such file"),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request
        );
    }
}
