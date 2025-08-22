package com.desafio.magaluAgendamento.controller.dto;

import com.desafio.magaluAgendamento.enums.TipoComunicacaoEnum;

import java.time.LocalDateTime;

public record AgendamentoDTORequest(


        LocalDateTime data,

        String destinatario,

        String msg,

        TipoComunicacaoEnum tipo

) {

}
