package com.backend.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> mensaje = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            mensaje.put(fieldName, errorMessage);
        });

        return mensaje;
    }


    @ExceptionHandler({UserDuplicateException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserDuplicateException(UserDuplicateException userDuplicateException ) {

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no registrado: " + userDuplicateException .getMessage());

        return mensaje;
    }


    @ExceptionHandler({NotFoundResourceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(NotFoundResourceException resourceNotFoundException) {

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no encontrado: " + resourceNotFoundException.getMessage());

        return mensaje;
    }


    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException violationException) {

        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no actualizado: " + violationException.getMessage());

        return mensaje;
    }






}
