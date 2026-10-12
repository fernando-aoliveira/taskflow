package br.com.taskflow.taskflow.strategy;

import br.com.taskflow.taskflow.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class OrdenarPorTitulo implements OrdenacaoTarefaStrategy {

    @Override
    public List<Tarefa> ordenar(List<Tarefa> tarefas) {
        return tarefas.stream()
                .sorted(Comparator.comparing(
                        Tarefa::getTitulo,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .toList();
    }
}
