package br.com.taskflow.taskflow;

import br.com.taskflow.taskflow.model.Tarefa;
import br.com.taskflow.taskflow.model.Prioridade;
import br.com.taskflow.taskflow.repository.TarefaRepository;
import br.com.taskflow.taskflow.service.TarefaService;
import br.com.taskflow.taskflow.strategy.OrdenarPorPrioridade;
import br.com.taskflow.taskflow.strategy.OrdenarPorTitulo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.taskflow.taskflow.model.StatusTarefa;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    private TarefaService tarefaService;

    @BeforeEach
    void configurar() {
        tarefaService = new TarefaService(
                tarefaRepository,
                new OrdenarPorPrioridade(),
                new OrdenarPorTitulo()
        );
    }

    @Test
    void deveSalvarUmaTarefa() {
        Tarefa tarefa = new Tarefa(
                "Estudar testes",
                "Aprender JUnit e Mockito",
                Prioridade.ALTA,
                null
        );

        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa resultado = tarefaService.criar(tarefa);

        assertSame(tarefa, resultado);
        verify(tarefaRepository).save(tarefa);
    }

    @Test
    void deveListarTodasAsTarefas() {
        List<Tarefa> tarefas = List.of(
                new Tarefa("Tarefa 1", "Descrição 1", Prioridade.ALTA, null),
                new Tarefa("Tarefa 2", "Descrição 2", Prioridade.BAIXA, null)
        );

        when(tarefaRepository.findAll()).thenReturn(tarefas);

        List<Tarefa> resultado = tarefaService.listarTodas();

        assertEquals(2, resultado.size());
        assertSame(tarefas, resultado);
        verify(tarefaRepository).findAll();
    }

    @Test
    void deveLancar404QuandoTarefaNaoExistir() {
        when(tarefaRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> tarefaService.buscarPorId(999L)
        );

        assertEquals(
                HttpStatus.NOT_FOUND,
                excecao.getStatusCode()
        );

        verify(tarefaRepository).findById(999L);
    }

    @Test
    void deveOrdenarTarefasPorTituloQuandoSolicitado() {
        Tarefa zebra = new Tarefa(
                "zebra", "Descrição", Prioridade.BAIXA, null);

        Tarefa alpha = new Tarefa(
                "Alpha", "Descrição", Prioridade.ALTA, null);

        when(tarefaRepository.findAll())
                .thenReturn(List.of(zebra, alpha));

        List<Tarefa> resultado =
                tarefaService.listarOrdenadas("titulo");

        assertEquals("Alpha", resultado.get(0).getTitulo());
        assertEquals("zebra", resultado.get(1).getTitulo());

        verify(tarefaRepository).findAll();
    }

    @Test
    void deveOrdenarTarefasPorPrioridadeQuandoSolicitado() {
        Tarefa baixa = new Tarefa(
                "Tarefa baixa", "Descrição", Prioridade.BAIXA, null);

        Tarefa alta = new Tarefa(
                "Tarefa alta", "Descrição", Prioridade.ALTA, null);

        Tarefa media = new Tarefa(
                "Tarefa média", "Descrição", Prioridade.MEDIA, null);

        when(tarefaRepository.findAll())
                .thenReturn(List.of(baixa, alta, media));

        List<Tarefa> resultado =
                tarefaService.listarOrdenadas("prioridade");

        assertEquals("Tarefa alta", resultado.get(0).getTitulo());
        assertEquals("Tarefa média", resultado.get(1).getTitulo());
        assertEquals("Tarefa baixa", resultado.get(2).getTitulo());

        verify(tarefaRepository).findAll();
    }

    @Test
    void deveLancar400QuandoCriterioDeOrdenacaoForInvalido() {
        when(tarefaRepository.findAll())
                .thenReturn(List.of());

        ResponseStatusException excecao = assertThrows(
                ResponseStatusException.class,
                () -> tarefaService.listarOrdenadas("data")
        );

        assertEquals(
                HttpStatus.BAD_REQUEST,
                excecao.getStatusCode()
        );

        verify(tarefaRepository).findAll();
    }

}
