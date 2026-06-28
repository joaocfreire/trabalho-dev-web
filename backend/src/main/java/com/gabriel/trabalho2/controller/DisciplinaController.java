package com.gabriel.trabalho2.controller;

import com.gabriel.trabalho2.model.Disciplina;
import com.gabriel.trabalho2.service.DisciplinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("disciplinas")
public class DisciplinaController {
    @Autowired
    private DisciplinaService disciplinaService;
    @PostMapping
    public Disciplina cadastrarDisciplina(@RequestBody @Valid Disciplina d){
        return disciplinaService.cadastrarDisciplina(d);
    }
}
