package com.gabriel.trabalho2.service;


import com.gabriel.trabalho2.exception.EntidadeNaoEncontradaException;
import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Inscrição;
import com.gabriel.trabalho2.model.Professor;
import com.gabriel.trabalho2.model.Turma;
import com.gabriel.trabalho2.repository.AlunoRepository;
import com.gabriel.trabalho2.repository.TurmaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;


    public Turma recuperarTurmaPorId(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Aluno com id = " + id + " não encontrado."));
    }


    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public Turma cadastrarTurma(Turma turma){return turmaRepository.save(turma);}

    public void removerTurmaPorId(Long id){turmaRepository.deleteById(id);}

    public List<Turma> recuperarTodasTurmas() {
        return turmaRepository.findAll();
    }

}
