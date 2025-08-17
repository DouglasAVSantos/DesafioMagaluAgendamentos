package com.desafio.magaluEstagio.serviceTestes;

import com.desafio.magaluEstagio.controller.dto.AgendamentoDTORequest;
import com.desafio.magaluEstagio.controller.dto.AgendamentoDTOResponse;
import com.desafio.magaluEstagio.enums.AgendamentoStatus;
import com.desafio.magaluEstagio.enums.TipoComunicacaoEnum;
import com.desafio.magaluEstagio.exception.BadRequestException;
import com.desafio.magaluEstagio.exception.NotFoundException;
import com.desafio.magaluEstagio.model.Agendamento;
import com.desafio.magaluEstagio.model.Pessoa;
import com.desafio.magaluEstagio.repository.AgendamentoRepository;
import com.desafio.magaluEstagio.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;


    private final AgendamentoDTOResponse dtoResponse = new AgendamentoDTOResponse(1l, LocalDateTime.now().plusHours(1), "Mensagem de teste", AgendamentoStatus.NOVO, TipoComunicacaoEnum.EMAIL, "Douglas");
    private AgendamentoDTORequest dtoRequest;
    private Agendamento agendamento;

    @BeforeEach
    void setup() {
        agendamento = new Agendamento(LocalDateTime.now().plusDays(1),
                "Mensagem de teste",
                new Pessoa("Douglas"),
                TipoComunicacaoEnum.EMAIL);
        agendamento.setId(1L);

        dtoRequest = new AgendamentoDTORequest(
                LocalDateTime.now().plusDays(1),
                "Douglas",
                "Mensagem de teste",
                TipoComunicacaoEnum.EMAIL
        );
    }

    @Test
    public void deveCadastrarUmAgendamentoComSucesso() {

        when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        AgendamentoDTOResponse response = agendamentoService.criarAgendamento(dtoRequest);
        assertEquals(1, response.id());
        assertEquals("Mensagem de teste", response.mensagem());
        assertEquals(AgendamentoStatus.NOVO, response.status());
        assertEquals(TipoComunicacaoEnum.EMAIL, response.tipo());
    }

    @Test
    public void deveLancarExcecaoQuandoNãoForPassadoDTO() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> agendamentoService.criarAgendamento(null));
        assertEquals("Requisição inválida.", ex.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoDataForNula() {
        AgendamentoDTORequest dto = new AgendamentoDTORequest(
                null,
                "Douglas",
                "Mensagem de teste",
                TipoComunicacaoEnum.EMAIL);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> agendamentoService.criarAgendamento(dto));
        assertEquals("A data é obrigatória.", ex.getMessage());

    }

    @Test
    public void deveLancarExcecaoQuandoDataForNoPassado() {
        //when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        AgendamentoDTORequest dto = new AgendamentoDTORequest(
                LocalDateTime.now().minusDays(1),
                "Douglas",
                "Mensagem de teste",
                TipoComunicacaoEnum.EMAIL);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> agendamentoService.criarAgendamento(dto));
        assertEquals("A data deve estar no futuro.", ex.getMessage());

    }

    @ParameterizedTest()
    @NullAndEmptySource
    public void deveLancarExcecaoQuandoMensagemForNulaOuBlank(String msgVazia) {
        //when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        AgendamentoDTORequest dto = new AgendamentoDTORequest(
                LocalDateTime.now().plusDays(1l),
                "Douglas",
                msgVazia,
                TipoComunicacaoEnum.EMAIL);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> agendamentoService.criarAgendamento(dto));
        assertEquals("A mensagem é obrigatória.", ex.getMessage());

    }
@ParameterizedTest
@NullAndEmptySource
    public void deveLancarExcecaoQuandoDestinatarioForNulaOuBlank(String destinatarioVazio) {
        //when(agendamentoRepository.save(any(Agendamento.class))).thenReturn(agendamento);
        AgendamentoDTORequest dto = new AgendamentoDTORequest(
                LocalDateTime.now().plusDays(1l),
                destinatarioVazio,
                "Mensagem Teste",
                TipoComunicacaoEnum.EMAIL);

        BadRequestException ex = assertThrows(BadRequestException.class, () -> agendamentoService.criarAgendamento(dto));
        assertEquals("O destinatario é obrigatório.", ex.getMessage());
    }

    @Test
    public void deveDevolverListaDeAgendamentosComSucesso() {
        Agendamento agendamento2 = new Agendamento(LocalDateTime.now().plusDays(1),
                "Mensagem de teste 2",
                new Pessoa("Pedro"),
                TipoComunicacaoEnum.EMAIL);
        agendamento.setId(2L);
        when(agendamentoRepository.findAll()).thenReturn(List.of(agendamento, agendamento2));
        List<AgendamentoDTOResponse> listaResponse = agendamentoService.getListaAgendamentoDTOResponse();
        assertEquals(2, listaResponse.size());
        assertEquals("Douglas", listaResponse.get(0).destinatario());
        assertEquals("Mensagem de teste 2", listaResponse.get(1).mensagem());
    }

    @Test
    public void deveRemoverAgendamentoComSucesso() {
        Agendamento agendamento2 = new Agendamento(LocalDateTime.now().plusDays(1),
                "Mensagem de teste 2",
                new Pessoa("Pedro"),
                TipoComunicacaoEnum.EMAIL);
        agendamento.setId(2L);
        when(agendamentoRepository.findById(agendamento2.getId())).thenReturn(Optional.of(agendamento2));

        agendamentoService.removerAgendamentoPorId(agendamento2.getId());

        verify(agendamentoRepository, times(1)).delete(agendamento2);
    }
    @Test
    public void deveBuscarAgendamentoComSucesso() {
        Agendamento agendamento2 = new Agendamento(LocalDateTime.now().plusDays(1),
                "Mensagem de teste 2",
                new Pessoa("Pedro"),
                TipoComunicacaoEnum.EMAIL);
        agendamento.setId(2L);
        when(agendamentoRepository.findById(agendamento2.getId())).thenReturn(Optional.of(agendamento2));

        AgendamentoDTOResponse response = agendamentoService.buscarAgendamentoPorId(agendamento2.getId());

        assertEquals("Mensagem de teste 2",response.mensagem());
        assertEquals("Pedro",response.destinatario());
        assertEquals(AgendamentoStatus.NOVO,response.status());
    }
@Test
    public void deveLancarExcecaoCasoIdInvalidoNaRemocao() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> agendamentoService.removerAgendamentoPorId(5L));
        assertEquals("Agendamento não encontrado.",ex.getMessage());
    }
    @Test
    public void deveLancarExcecaoCasoIdInvalidoNaBusca() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> agendamentoService.buscarAgendamentoPorId(5L));
        assertEquals("Agendamento não encontrado.",ex.getMessage());
    }
}

