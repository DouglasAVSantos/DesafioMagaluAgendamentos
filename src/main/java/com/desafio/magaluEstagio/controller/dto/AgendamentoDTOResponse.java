package com.desafio.magaluEstagio.controller.dto;

import com.desafio.magaluEstagio.enums.AgendamentoStatus;
import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public record AgendamentoDTOResponse(
        @JsonIgnore
        Long id,

        LocalDateTime data,

        String mensagem,

        AgendamentoStatus status,

        TipoComunicacaoEnum tipo,

        String destinatario
) {
}
