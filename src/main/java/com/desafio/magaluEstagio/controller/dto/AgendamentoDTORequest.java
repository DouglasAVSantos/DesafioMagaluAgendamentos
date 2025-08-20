package com.desafio.magaluEstagio.controller.dto;

import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AgendamentoDTORequest(


        LocalDateTime data,

        String destinatario,

        String msg,

        TipoComunicacaoEnum tipo

) {

}
