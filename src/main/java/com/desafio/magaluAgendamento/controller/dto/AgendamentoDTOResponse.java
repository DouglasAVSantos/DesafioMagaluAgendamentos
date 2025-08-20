package com.desafio.magaluAgendamento.controller.dto;

import com.desafio.magaluAgendamento.enums.AgendamentoStatus;
import com.desafio.magaluAgendamento.enums.TipoComunicacaoEnum;
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
