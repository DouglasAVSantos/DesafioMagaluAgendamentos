package com.desafio.magaluEstagio.serviceTestes;

import com.desafio.magaluEstagio.controller.AgendamentoController;
import com.desafio.magaluEstagio.controller.dto.AgendamentoDTORequest;
import com.desafio.magaluEstagio.controller.dto.AgendamentoDTOResponse;
import com.desafio.magaluEstagio.enums.AgendamentoStatus;
import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import com.desafio.magaluEstagio.exception.BadRequestException;
import com.desafio.magaluEstagio.exception.NotFoundException;
import com.desafio.magaluEstagio.service.AgendamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AgendamentoController.class)
public class AgendamentoControllerTeste {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendamentoService agendamentoService;

    private AgendamentoDTORequest dtoRequest;

    private AgendamentoDTOResponse dtoResponse;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setup() {
        dtoRequest = new AgendamentoDTORequest(LocalDateTime.now().plusDays(1), "Douglas",
                "Mensagem do teste do controller", TipoComunicacaoEnum.EMAIL);

        dtoResponse = new AgendamentoDTOResponse(
                1L,
                dtoRequest.data(),
                dtoRequest.msg(),
                AgendamentoStatus.NOVO,
                dtoRequest.tipo(),
                dtoRequest.destinatario()
        );
    }

    @Test
    public void deveDevolverStatusCREATEDquandoChamarCriarAgendamento() throws Exception {
        when(agendamentoService.criarAgendamento(dtoRequest)).thenReturn(dtoResponse);

        mockMvc.perform(post("/agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.destinatario").value("Douglas"))
                .andExpect(jsonPath("$.mensagem").value("Mensagem do teste do controller"));

        verify(agendamentoService, times(1)).criarAgendamento(dtoRequest);
    }

    @Test
    public void deveDevolverStatusOKquandoChamarBuscarAgendamento() throws Exception {
        Long id = 1L;
        when(agendamentoService.buscarAgendamentoPorId(id)).thenReturn(dtoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/agendamento/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.destinatario").value("Douglas"))
                .andExpect(jsonPath("$.mensagem").value("Mensagem do teste do controller"));

        verify(agendamentoService, times(1)).buscarAgendamentoPorId(id);
    }

    @Test
    public void deveDevolverStatusNotFoundQuandoChamarBuscarAgendamento() throws Exception {
        Long id = 1L;
        when(agendamentoService.buscarAgendamentoPorId(id)).thenThrow(new NotFoundException("não encontrado."));

        mockMvc.perform(MockMvcRequestBuilders.get("/agendamento/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("não encontrado."));

        verify(agendamentoService, times(1)).buscarAgendamentoPorId(id);
    }

    @Test
    public void deveDevolverStatusOKquandoChamarDeletarAgendamento() throws Exception {
        Long id = 1L;
        doNothing().when(agendamentoService).removerAgendamentoPorId(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/agendamento/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Agendamento de id " + id + " deletado com sucesso."));

        verify(agendamentoService, times(1)).removerAgendamentoPorId(id);
    }

    @Test
    public void deveDevolverStatusNotFoundQuandoChamarDeletarAgendamento() throws Exception {
        Long id = 1L;
        doThrow(new NotFoundException("não encontrado.")).when(agendamentoService).removerAgendamentoPorId(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/agendamento/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.erro").value("não encontrado."));

        verify(agendamentoService, times(1)).removerAgendamentoPorId(id);
    }

    @Test
    public void deveDevolverStatusOKquandoChamarFindAllAgendamentos() throws Exception {
        List<AgendamentoDTOResponse> lista = List.of(dtoResponse);

        when(agendamentoService.getListaAgendamentoDTOResponse()).thenReturn(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/agendamento").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(lista)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("EMAIL"))
                .andExpect(jsonPath("$[0].destinatario").value("Douglas"))
                .andExpect(jsonPath("$[0].mensagem").value("Mensagem do teste do controller"));
    }

    @Test
    public void deveDevolverStatusNOTFOUNDquandoChamarFindAllAgendamentos() throws Exception {
        List<AgendamentoDTOResponse> lista = List.of();

        when(agendamentoService.getListaAgendamentoDTOResponse()).thenReturn(lista);

        mockMvc.perform(MockMvcRequestBuilders.get("/agendamento").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(lista)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void deveDevolverStatusBADREQUESTQuandoChamarCriarAgendamento() throws Exception {

        AgendamentoDTORequest request = new AgendamentoDTORequest(
                LocalDateTime.now().plusHours(1), null, "mensagem", TipoComunicacaoEnum.EMAIL
        );

        when(agendamentoService.criarAgendamento(any(AgendamentoDTORequest.class))).thenThrow(new BadRequestException("erro"));

        mockMvc.perform(post("/agendamento").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("erro"));

        verify(agendamentoService, times(1)).criarAgendamento(any(AgendamentoDTORequest.class));
    }

}

