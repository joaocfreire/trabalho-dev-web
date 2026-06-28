package br.uff.ic.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.uff.ic.model.Professor;
import br.uff.ic.model.Turma;
import br.uff.ic.service.TurmaService;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("turmas")   // htttp://localhost:8080/turmas
public class TurmaController {
    private final TurmaService turmaService;

    @GetMapping("{idTurma}")
    public ResponseEntity<Turma> recuperarTurmaPorId(@PathVariable("idTurma") Long id) {
        Turma turma = turmaService.recuperarTurmaPorId(id);
        return ResponseEntity.ok(turma);
    }


    @PostMapping
    public Turma cadastrarTurma(@RequestBody @Valid Turma turma){return turmaService.cadastrarTurma(turma);}


    @DeleteMapping("{idTurma}")
    public ResponseEntity<Void>  removerTurmaPorId(@PathVariable("idTurma") Long id)
    {turmaService.removerTurmaPorId(id);
        return ResponseEntity.ok().build();}


    @GetMapping
    public List<Turma> recuperarTodasTurmas() {
        return turmaService.recuperarTodasTurmas();
    }

}
