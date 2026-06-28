package br.uff.ic.controller;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.uff.ic.model.Aluno;
import br.uff.ic.service.AlunoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("alunos")   // http://localhost:8080/alunos
public class AlunoController {
    private final AlunoService alunoService;

    @GetMapping
    public List<Aluno> recuperarAlunos() {
        return alunoService.recuperarAlunos();
    }

    @PostMapping
    public Aluno cadastrarAluno(@RequestBody @Valid Aluno aluno) {
        return alunoService.cadastrarAluno(aluno);
    }

    @PutMapping
    public Aluno alterarAluno(@RequestBody @Valid Aluno aluno) {
        return alunoService.alterarAluno(aluno);
    }

    @GetMapping("{idAluno}")
    public Aluno recuperarAlunoPorId(@PathVariable("idAluno")  Long id) {return alunoService.recuperarAlunoPorId(id);}


    // Implementação utilizando um builder
    // Delete para http://localhost:8080/produtos/1
    @DeleteMapping("{idAluno}")
    public ResponseEntity<Void> removerAlunoPorId(@PathVariable("idAluno") Long id) {
        alunoService.removerAlunoPorId(id);
        return ResponseEntity.ok().build();
    }
}