package com.desafio.magaluAgendamento.service;

import com.desafio.magaluAgendamento.controller.dto.AgendamentoDTORequest;
import com.desafio.magaluAgendamento.controller.dto.AgendamentoDTOResponse;
import com.desafio.magaluAgendamento.enums.AgendamentoStatus;
import com.desafio.magaluAgendamento.exception.BadRequestException;
import com.desafio.magaluAgendamento.exception.NotFoundException;
import com.desafio.magaluAgendamento.model.Agendamento;
import com.desafio.magaluAgendamento.model.Pessoa;
import com.desafio.magaluAgendamento.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private Logger log = LoggerFactory.getLogger(AgendamentoService.class);

    public AgendamentoDTOResponse criarAgendamento(AgendamentoDTORequest dto) {
        validar(dto);
        Agendamento agendamento = new Agendamento(dto.data(), dto.msg(), new Pessoa(dto.destinatario()), dto.tipo(), AgendamentoStatus.AGENDADO);
        return toResponse(agendamentoRepository.save(agendamento));
    }

    public AgendamentoDTOResponse toResponse(Agendamento agendamento) {
        return (new AgendamentoDTOResponse(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getMensagem(),
                agendamento.getStatus(),
                agendamento.getTipoComunicacao(),
                agendamento.getDestinatario().getNome()));
    }

    public List<AgendamentoDTOResponse> getListaAgendamentoDTOResponse() {
        return agendamentoRepository.findAll().stream().map(c -> toResponse(c)).collect(Collectors.toList());
    }

    public void removerAgendamentoPorId(Long id) {

        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> {
            log.error("Agendamento não encontrado. ID: "+id);
            return new NotFoundException("Agendamento não encontrado.");});
        agendamentoRepository.delete(agendamento);

    }

    public AgendamentoDTOResponse buscarAgendamentoPorId(Long id) {

        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> {
                log.error("Agendamento não encontrado. ID: "+id);
                return new NotFoundException("Agendamento não encontrado.");});
        return toResponse(agendamento);
    }

    public AgendamentoDTORequest validar(AgendamentoDTORequest dto) {
        if (dto == null) {
            log.error("Requisição Inválida.");
            throw new NotFoundException("Requisição inválida.");
        }
        if (dto.data() == null) {
            log.error("A data é obrigatória.");
            throw new BadRequestException("A data é obrigatória.");
        }
        if (dto.data().isBefore(LocalDateTime.now())) {
            log.error("A data deve estar no futuro.");
            throw new BadRequestException("A data deve estar no futuro.");
        }

        if (dto.msg() == null || dto.msg().isBlank()) {
            log.error("A mensagem é obrigatória.");
            throw new BadRequestException("A mensagem é obrigatória.");
        }
        if (dto.destinatario() == null || dto.destinatario().isBlank()) {
            log.error("O destinarario é obrigatório.");
            throw new BadRequestException("O destinatario é obrigatório.");
        }
        return dto;
    }


}





