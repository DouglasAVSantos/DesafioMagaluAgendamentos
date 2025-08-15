package com.desafio.magaluEstagio.controller.dto;

import com.desafio.magaluEstagio.model.Agendamento;

import java.time.LocalDate;

public record ListaDeAgendamentosDTO(LocalDate data,
                                     String destinatario,
                                     String msg) {


    public ListaDeAgendamentosDTO(Agendamento agendamento){
        this(agendamento.getData(),agendamento.getDestinatario().getNome(), agendamento.getMensagem());
    }
}
