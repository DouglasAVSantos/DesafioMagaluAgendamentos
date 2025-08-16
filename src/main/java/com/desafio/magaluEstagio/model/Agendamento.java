package com.desafio.magaluEstagio.model;

import com.desafio.magaluEstagio.enums.AgendamentoStatus;
import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime data;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;
    @Enumerated(EnumType.STRING)
    private AgendamentoStatus status = AgendamentoStatus.NOVO;
    @Enumerated(EnumType.STRING)
    private TipoComunicacaoEnum comunicacao;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")
    private Pessoa destinatario;

    public Agendamento() {
    }

    public Agendamento(LocalDateTime data, String msg, Pessoa destinatario, TipoComunicacaoEnum tipo) {
        this.data = data;
        this.mensagem = msg;
        this.destinatario = destinatario;
        this.comunicacao = tipo;
        this.status = AgendamentoStatus.NOVO;
    }

    public Agendamento(LocalDateTime data, String msg, Pessoa destinatario, TipoComunicacaoEnum tipo, AgendamentoStatus status) {
        this.data = data;
        this.mensagem = msg;
        this.destinatario = destinatario;
        this.comunicacao = tipo;
        this.status = status;
    }
}

