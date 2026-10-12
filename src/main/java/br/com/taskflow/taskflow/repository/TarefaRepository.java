package br.com.taskflow.taskflow.repository;

import br.com.taskflow.taskflow.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
