package br.uff.ic.service;


import org.springframework.stereotype.Service;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Aluno;
import br.uff.ic.model.Inscrição;
import br.uff.ic.model.Professor;
import br.uff.ic.model.Turma;
import br.uff.ic.repository.AlunoRepository;
import br.uff.ic.repository.TurmaRepository;

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
