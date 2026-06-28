package com.gabriel.trabalho2.controller;


import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Inscrição;
import com.gabriel.trabalho2.model.ResultadoPaginado;
import com.gabriel.trabalho2.service.InscricaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("inscricoes")   // htttp://localhost:8080/inscricoes
public class InscricaoController {
    private final InscricaoService inscricaoService;

    @PostMapping
    public Inscrição cadastrarInscricao(@RequestBody @Valid Inscrição inscrição){return inscricaoService.cadastrarInscricao(inscrição);}

    @DeleteMapping("{idInscricao}")
    public ResponseEntity<Void>  removerInscricaoPorId(@PathVariable("idInscricao") Long id)
    {inscricaoService.removerInscricaoPorId(id);
        return ResponseEntity.ok().build();}

    @GetMapping
    public List<Inscrição> recuperarInscricoes() {
        return inscricaoService.recuperarInscricoes();
    }
    @GetMapping("turma={idTurma}")
    public List<Inscrição> recuperarInscricoesPorTurma(@PathVariable("idTurma") Long id) {
        return inscricaoService.recuperarInscricoesPorTurma(id);
    }
    @GetMapping("paginacao")
    public ResultadoPaginado<Inscrição> recuperarInscricoesPorTurmaComPaginacao(@RequestParam(name="pagina", defaultValue = "0")int pagina, @RequestParam(name="tamanho", defaultValue = "5")int tamanho, @RequestParam(name="turma", defaultValue = "")Long turma){
        Pageable pageable = PageRequest.of(pagina, tamanho);
        Page<Inscrição> page = inscricaoService.recuperarInscricoesPorTurmaComPaginacao(pageable, turma);
        return new ResultadoPaginado<Inscrição>(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getContent());
    }

}
