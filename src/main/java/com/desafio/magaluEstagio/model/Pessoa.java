package com.desafio.magaluEstagio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String email;
    private String celular;
    @OneToMany(mappedBy = "destinatario")
    @JsonIgnore
    private List<Agendamento> listaDeAgendamentos;

    public Pessoa() {
    }

    public Pessoa(String nome){
        this.nome = nome;
    }
}


