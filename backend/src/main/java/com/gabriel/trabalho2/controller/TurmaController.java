package com.gabriel.trabalho2.controller;
import com.gabriel.trabalho2.model.Professor;
import com.gabriel.trabalho2.model.Turma;
import com.gabriel.trabalho2.service.TurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
