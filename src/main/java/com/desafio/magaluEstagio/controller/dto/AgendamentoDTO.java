package com.desafio.magaluEstagio.controller.dto;

import com.desafio.magaluEstagio.model.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AgendamentoDTO(
        @NotNull LocalDate data,
        @NotNull String destinatario,
        @NotBlank String msg

        ){

}
