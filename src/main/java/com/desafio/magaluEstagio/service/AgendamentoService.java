package com.desafio.magaluEstagio.service;

import com.desafio.magaluEstagio.controller.dto.AgendamentoDTORequest;
import com.desafio.magaluEstagio.controller.dto.AgendamentoDTOResponse;
import com.desafio.magaluEstagio.enums.AgendamentoStatus;
import com.desafio.magaluEstagio.exception.BadRequestException;
import com.desafio.magaluEstagio.exception.NotFoundException;
import com.desafio.magaluEstagio.model.Agendamento;
import com.desafio.magaluEstagio.model.Pessoa;
import com.desafio.magaluEstagio.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

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

        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento não encontrado."));
        agendamentoRepository.delete(agendamento);

    }

    public AgendamentoDTOResponse buscarAgendamentoPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Agendamento não encontrado."));
        return toResponse(agendamento);
    }

    public AgendamentoDTORequest validar(AgendamentoDTORequest dto){
        if(dto == null){
            throw new NotFoundException("Requisição inválida.");
        }
        if(dto.data() == null){
            throw new BadRequestException("A data é obrigatória.");
        }
        if(dto.data().isBefore(LocalDateTime.now())){
            throw new BadRequestException("A data deve estar no futuro.");
        }

        if(dto.msg() == null || dto.msg().isBlank()){
            throw new BadRequestException("A mensagem é obrigatória.");
        }
        if(dto.destinatario() == null || dto.destinatario().isBlank()){
            throw new BadRequestException("O destinatario é obrigatório.");
        }
        return dto;
    }


}





