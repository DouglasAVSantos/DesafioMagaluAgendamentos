package com.desafio.magaluAgendamento.repository;


import com.desafio.magaluAgendamento.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {
}
