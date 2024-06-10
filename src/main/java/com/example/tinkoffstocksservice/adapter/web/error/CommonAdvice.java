package com.example.tinkoffstocksservice.adapter.web.error;

import com.example.tinkoffstocksservice.adapter.web.error.common.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleConstraints(ConstraintViolationException e) {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.VALIDATION_ERROR, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = new ArrayList<>(ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErrorResponse(ErrorCode.VALIDATION_ERROR, e.getField(), e.getDefaultMessage())).toList());
        errors.addAll(ex.getBindingResult().getGlobalErrors().stream()
                .map(e -> new ErrorResponse(ErrorCode.VALIDATION_ERROR, e.getDefaultMessage())).toList());
        return ResponseEntity.badRequest()
                .body(errors);
    }
}
