package com.desafio.magaluAgendamento.controller;

import com.desafio.magaluAgendamento.exception.BadRequestException;
import com.desafio.magaluAgendamento.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<Map<String, String>> handlerBadResquest(BadRequestException e) {
        return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
    }

    @ExceptionHandler
    ResponseEntity<Map<String, String>> handlerNotFoundResquest(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
    }
}
