
package br.com.taskflow.taskflow.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "A prioridade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;

    public Tarefa() {
    }

    public Tarefa(String titulo, String descricao,
                  Prioridade prioridade, StatusTarefa status) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }
}
