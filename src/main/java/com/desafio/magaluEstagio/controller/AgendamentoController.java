package com.desafio.magaluEstagio.controller;

import com.desafio.magaluEstagio.controller.dto.AgendamentoDTO;
import com.desafio.magaluEstagio.controller.dto.ListaDeAgendamentosDTO;
import com.desafio.magaluEstagio.model.Agendamento;
import com.desafio.magaluEstagio.model.Pessoa;
import com.desafio.magaluEstagio.repository.AgendamentoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/agendamento")
public class AgendamentoController {

    @Autowired
    AgendamentoRepository agendamentoRepository;

    Logger log = LoggerFactory.getLogger(AgendamentoController.class);

    @PostMapping
    @Transactional
    @Operation(description = "Este Endpoint cadastra os agendamentos")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "201",description = "Agendamento cadastrado com sucesso."),
            @ApiResponse(responseCode = "500",description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "400",description = "Campos Inválidos")
    })
    ResponseEntity<AgendamentoDTO> criarAgendamento(@RequestBody(required = false) @Valid AgendamentoDTO dto){
        log.info("Tentando Criar o Agendamento...");
        agendamentoRepository.save(new Agendamento(dto.data(),new Pessoa(dto.destinatario()),dto.msg()));
        log.info("Agendamento criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    @Transactional
    @Operation(description = "Este endpoint retorna todos os agendamentos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Lista de Agendamentos Retornada com sucesso"),
            @ApiResponse(responseCode = "404",description = "Lista de agendamentos vazia")
    })
    ResponseEntity<List<ListaDeAgendamentosDTO>> findAllAgendamentos(){
        log.info("Iniciando busca por lista de agendamentos...");

        List<Agendamento> lista = agendamentoRepository.findAll();
        //List<AgendamentoDTO> listaDTO = lista.stream().map(c->
        //        new AgendamentoDTO(c.getData(),c.getDestinatario().getNome(),c.getMensagem())).collect(Collectors.toList());
        if(lista.isEmpty()){
            log.info("A lista esta vazia.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        log.info("Lista retornada com sucesso");
        //return listaDTO;
        return ResponseEntity.ok(lista.stream().map(ListaDeAgendamentosDTO::new).toList());
    }

}
