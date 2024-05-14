package com.example.tinkoffstocksservice.adapter.web.errors;

import com.example.tinkoffstocksservice.adapter.web.errors.common.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class CommonAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler
    public List<ErrorResponse> handleConstraints(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream().map(ex -> new ErrorResponse(ErrorCode.VALIDATION_ERROR, ex.getPropertyPath().toString(), ex.getMessage())).toList();
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
