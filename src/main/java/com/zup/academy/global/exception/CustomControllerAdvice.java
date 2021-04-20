package com.zup.academy.global.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static java.util.stream.Collectors.*;

@RestControllerAdvice
public class CustomControllerAdvice {

    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception){

        final var campos = exception.getBindingResult().getFieldErrors().stream()
                .filter(it -> it.getDefaultMessage() != null)
                .filter(it -> !it.getDefaultMessage().isBlank())
                .collect(groupingBy(FieldError::getField,mapping(DefaultMessageSourceResolvable::getDefaultMessage,toList())));

        return ResponseEntity.badRequest().body(Map.of("mensagem","requisição invalida",
                "campos",campos));

    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException exception){
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("status",exception.getStatus().value());
        map.put("path","");
        map.put("error",exception.getStatus().getReasonPhrase());
        map.put("message",exception.getLocalizedMessage());
        return ResponseEntity.status(exception.getStatus()).body(map);
    }
}