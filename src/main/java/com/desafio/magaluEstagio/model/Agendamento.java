package com.desafio.magaluEstagio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Table
@Getter
@Setter
@Entity
public class Agendamento {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate data;
    private String mensagem;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) @JoinColumn(name = "pessoa_id")
    private Pessoa destinatario;

    public Agendamento() {
    }

    public Agendamento(LocalDate data, Pessoa destinatario, String mensagem){
        this.data = data;
        this.mensagem = mensagem;
        this.destinatario = destinatario;
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                ", data=" + data +
                ", destinatario='" + destinatario.getNome() + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", pessoa=" + destinatario +
                '}';
    }
}
