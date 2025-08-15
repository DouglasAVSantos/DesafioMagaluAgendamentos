package com.desafio.magaluEstagio.repository;

import com.desafio.magaluEstagio.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {

}
