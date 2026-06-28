package com.gabriel.trabalho2.controller;
import com.gabriel.trabalho2.exception.EntidadeNaoEncontradaException;
import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Professor;
import com.gabriel.trabalho2.repository.ProfessorRepository;
import com.gabriel.trabalho2.service.AlunoService;
import com.gabriel.trabalho2.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("professores")   // htttp://localhost:8080/professores
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorRepository professorRepository;

    @GetMapping
    public List<Professor> recuperarTodosProfessores() {
        return professorService.recuperarTodosProfessores();
    }

    @PostMapping
    public Professor cadastrarProfessor(@RequestBody @Valid Professor professor) {
        return professorService.cadastrarProfessor(professor);
    }

    @PutMapping
    public Professor alterarProfessor(@RequestBody @Valid Professor professor) {

        // Este método precisa ser transacional uma vez que o método abaixo
        // efetua um lock na linha da tabela referente ao produto recuperado.
        // Observe que o método recuperarProdutoPorIdComLock retorna um
        // Optional<Produto>

        return professorService.alterarProfessor(professor);
    }

    @GetMapping("{idProfessor}")
    public Professor recuperarProfessorPorId(@PathVariable("idProfessor") Long id) {return professorService.recuperarProfessorPorId(id);}

    // Implementação utilizando um builder
    // Delete para http://localhost:8080/produtos/1
    @DeleteMapping("{idProfessor}")
    public ResponseEntity<Void> removerProfessorPorId(@PathVariable("idProfessor") Long id) {
        professorService.removerProfessorPorId(id);
        return ResponseEntity.ok().build();
    }
}