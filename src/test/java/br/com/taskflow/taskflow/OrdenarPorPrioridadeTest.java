package br.com.taskflow.taskflow;

import br.com.taskflow.taskflow.model.Prioridade;
import br.com.taskflow.taskflow.model.Tarefa;
import br.com.taskflow.taskflow.strategy.OrdenarPorPrioridade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrdenarPorPrioridadeTest {

    @Test
    void deveOrdenarTarefasDaMaiorParaAMenorPrioridade() {

        // Arrange: preparar os dados
        Tarefa baixa = new Tarefa(
                "Tarefa baixa", "Descrição", Prioridade.BAIXA, null);

        Tarefa alta = new Tarefa(
                "Tarefa alta", "Descrição", Prioridade.ALTA, null);

        Tarefa media = new Tarefa(
                "Tarefa média", "Descrição", Prioridade.MEDIA, null);

        List<Tarefa> tarefas = List.of(baixa, alta, media);

        // Act: executar o comportamento que queremos testar
        OrdenarPorPrioridade estrategia = new OrdenarPorPrioridade();

        List<Tarefa> resultado = estrategia.ordenar(tarefas);

        // Assert: verificar se a ordem está correta
        assertEquals("Tarefa alta", resultado.get(0).getTitulo());
        assertEquals("Tarefa média", resultado.get(1).getTitulo());
        assertEquals("Tarefa baixa", resultado.get(2).getTitulo());
    }
}
