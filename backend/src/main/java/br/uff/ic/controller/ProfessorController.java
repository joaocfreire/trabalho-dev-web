package br.uff.ic.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Aluno;
import br.uff.ic.model.Professor;
import br.uff.ic.repository.ProfessorRepository;
import br.uff.ic.service.AlunoService;
import br.uff.ic.service.ProfessorService;

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