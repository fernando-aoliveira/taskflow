
package br.com.taskflow.taskflow.controller;

import br.com.taskflow.taskflow.model.Tarefa;
import br.com.taskflow.taskflow.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criar(
            @Valid @RequestBody Tarefa tarefa) {

        Tarefa tarefaSalva = tarefaService.criar(tarefa);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tarefaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas(
            @RequestParam(required = false) String ordenacao) {

        if (ordenacao == null || ordenacao.isBlank()) {
            return ResponseEntity.ok(tarefaService.listarTodas());
        }

        return ResponseEntity.ok(
                tarefaService.listarOrdenadas(ordenacao)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }
}
