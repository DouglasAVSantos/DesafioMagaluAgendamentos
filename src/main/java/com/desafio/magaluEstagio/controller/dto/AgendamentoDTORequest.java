package com.desafio.magaluEstagio.controller.dto;

import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoDTORequest(
        @NotNull(message = "A data não pode ser nula")
        @Future(message = "A data deve estar no futuro.")
        LocalDateTime data,
        @NotBlank(message = "O destinatario é obrigatorio")
        String destinatario,
        @NotBlank(message = "A mensagem não pode estar vazia.")
        String msg,
        @NotNull(message = "O tipo de comunicação é obrigatório")
        TipoComunicacaoEnum tipo

) {

}
