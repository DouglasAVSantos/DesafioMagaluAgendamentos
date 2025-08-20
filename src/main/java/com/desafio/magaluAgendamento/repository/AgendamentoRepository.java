package com.desafio.magaluAgendamento.repository;

import com.desafio.magaluAgendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {

}
