package com.desafio.magaluAgendamento.controller;

import com.desafio.magaluAgendamento.controller.dto.AgendamentoDTORequest;
import com.desafio.magaluAgendamento.controller.dto.AgendamentoDTOResponse;
import com.desafio.magaluAgendamento.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {


    private final AgendamentoService agendamentoService;

    Logger log = LoggerFactory.getLogger(AgendamentoController.class);

    @PostMapping
    @Transactional
    @Operation(description = "Este Endpoint cadastra os agendamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor"),
            @ApiResponse(responseCode = "400", description = "Campos Inválidos")
    })
    ResponseEntity<AgendamentoDTOResponse> criarAgendamento(@RequestBody(required = false) AgendamentoDTORequest dto) {
        log.info("Tentando Criar o Agendamento...");
        AgendamentoDTOResponse response = agendamentoService.criarAgendamento(dto);
        log.info("Agendamento criado com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Transactional
    @Operation(description = "Este endpoint retorna todos os agendamentos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de Agendamentos Retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de agendamentos vazia")
    })
    ResponseEntity<List<AgendamentoDTOResponse>> findAllAgendamentos() {
        log.info("Iniciando busca por lista de agendamentos...");
        List<AgendamentoDTOResponse> lista = agendamentoService.getListaAgendamentoDTOResponse();
        if (lista.isEmpty()) {
            log.info("A lista esta vazia.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        log.info("Lista retornada com sucesso");
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    @Transactional
    @Operation(description = "Este endpoint é responsável por deletar um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento retornado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado."),
            @ApiResponse(responseCode = "400", description = "Campos Inválidos"),
    })
    ResponseEntity<AgendamentoDTOResponse> buscarAgendamento(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarAgendamentoPorId(id));

    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(description = "Este endpoint é responsável por deletar um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado."),
            @ApiResponse(responseCode = "400", description = "Campos Inválidos"),
    })
    ResponseEntity<Map<String, String>> deletarAgendamento(@PathVariable Long id) {
        agendamentoService.removerAgendamentoPorId(id);
        return ResponseEntity.ok(Map.of("mensagem", "Agendamento de id " + id + " deletado com sucesso."));
    }

}
