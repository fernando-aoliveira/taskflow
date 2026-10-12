
package br.com.taskflow.taskflow.service;

import br.com.taskflow.taskflow.model.Tarefa;
import br.com.taskflow.taskflow.repository.TarefaRepository;
import br.com.taskflow.taskflow.strategy.OrdenacaoTarefaStrategy;
import br.com.taskflow.taskflow.strategy.OrdenarPorPrioridade;
import br.com.taskflow.taskflow.strategy.OrdenarPorTitulo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final OrdenarPorPrioridade ordenarPorPrioridade;
    private final OrdenarPorTitulo ordenarPorTitulo;

    public TarefaService(
            TarefaRepository tarefaRepository,
            OrdenarPorPrioridade ordenarPorPrioridade,
            OrdenarPorTitulo ordenarPorTitulo) {
        this.tarefaRepository = tarefaRepository;
        this.ordenarPorPrioridade = ordenarPorPrioridade;
        this.ordenarPorTitulo = ordenarPorTitulo;
    }

    public Tarefa criar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarOrdenadas(String ordenacao) {
        List<Tarefa> tarefas = tarefaRepository.findAll();

        OrdenacaoTarefaStrategy strategy = switch (ordenacao.toLowerCase()) {
            case "prioridade" -> ordenarPorPrioridade;
            case "titulo" -> ordenarPorTitulo;
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ordenação inválida. Utilize 'prioridade' ou 'titulo'."
            );
        };

        return strategy.ordenar(tarefas);
    }

    public Tarefa buscarPorId(Long id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tarefa não encontrada"
                ));
    }
}
