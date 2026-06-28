package br.uff.ic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.uff.ic.model.Disciplina;
import br.uff.ic.service.DisciplinaService;

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
