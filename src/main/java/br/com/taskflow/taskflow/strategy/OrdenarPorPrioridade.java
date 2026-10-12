package br.com.taskflow.taskflow.strategy;

import br.com.taskflow.taskflow.model.Prioridade;
import br.com.taskflow.taskflow.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class OrdenarPorPrioridade implements OrdenacaoTarefaStrategy {

    @Override
    public List<Tarefa> ordenar(List<Tarefa> tarefas) {
        return tarefas.stream()
                .sorted(Comparator.comparingInt(
                        tarefa -> obterPeso(tarefa.getPrioridade())
                ))
                .toList();
    }

    private int obterPeso(Prioridade prioridade) {
        return switch (prioridade) {
            case ALTA -> 1;
            case MEDIA -> 2;
            case BAIXA -> 3;
        };
    }
}
