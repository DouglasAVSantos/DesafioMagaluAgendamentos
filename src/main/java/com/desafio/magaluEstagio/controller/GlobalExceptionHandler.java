package com.desafio.magaluEstagio.controller;

import com.desafio.magaluEstagio.exception.BadRequestExeption;
import com.desafio.magaluEstagio.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler
    ResponseEntity<String> handlerBadResquest(BadRequestExeption e){
        return ResponseEntity.badRequest().body("Dados Inválidos. Erro: "+e.getMessage());
    }
    @ExceptionHandler
    ResponseEntity<String> handlerNotFoundResquest(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não Encontrado. Erro: "+e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Erro de validação");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensagem);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<String> handlerInternalServerError(Exception e){
        return ResponseEntity.internalServerError().body("Erro interno do servidor: "+e.getMessage());
    }

}
