package com.desafio.magaluEstagio.controller;

import com.desafio.magaluEstagio.exception.BadRequestExeption;
import com.desafio.magaluEstagio.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<Map<String, String>> handlerBadResquest(BadRequestExeption e) {
        return ResponseEntity.badRequest().body(Map.of("Dados Inválidos. Erro: ", e.getMessage()));
    }

    @ExceptionHandler
    ResponseEntity<Map<String, String>> handlerNotFoundResquest(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Erro: ", e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst().map(c -> c.getDefaultMessage())
                .orElse("Erro de validação");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("Erro: ", mensagem));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<Map<String, String>> handlerInternalServerError(Exception e) {
        return ResponseEntity.internalServerError().body(Map.of("Erro interno do servidor: ", e.getMessage()));
    }

}
