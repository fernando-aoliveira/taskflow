package br.com.taskflow.taskflow.strategy;

import br.com.taskflow.taskflow.model.Tarefa;
import java.util.List;

public interface OrdenacaoTarefaStrategy {

    List<Tarefa> ordenar(List<Tarefa> tarefas);
}
