
package br.com.taskflow.taskflow;

import br.com.taskflow.taskflow.model.Prioridade;
import br.com.taskflow.taskflow.model.Tarefa;
import br.com.taskflow.taskflow.strategy.OrdenarPorTitulo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
class OrdenarPorTituloTest {

    @Test
    void deveOrdenarTitulosAlfabeticamenteIgnorandoMaiusculas() {

        // Arrange: preparar as tarefas fora de ordem
        Tarefa zebra = new Tarefa(
                "zebra", "Descrição", Prioridade.BAIXA, null);

        Tarefa alpha = new Tarefa(
                "Alpha", "Descrição", Prioridade.MEDIA, null);

        Tarefa beta = new Tarefa(
                "beta", "Descrição", Prioridade.ALTA, null);

        List<Tarefa> tarefas = List.of(zebra, alpha, beta);

        // Act: executar a estratégia
        OrdenarPorTitulo estrategia = new OrdenarPorTitulo();

        List<Tarefa> resultado = estrategia.ordenar(tarefas);

        // Assert: conferir a ordem alfabética
        assertEquals("Alpha", resultado.get(0).getTitulo());
        assertEquals("beta", resultado.get(1).getTitulo());
        assertEquals("zebra", resultado.get(2).getTitulo());
    }
}
