package com.gabriel.trabalho2.service;


import com.gabriel.trabalho2.exception.EntidadeNaoEncontradaException;
import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Professor;
import com.gabriel.trabalho2.repository.AlunoRepository;
import com.gabriel.trabalho2.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> recuperarTodosProfessores() {
        return professorRepository.findAll();
    }

    public Professor cadastrarProfessor(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor alterarProfessor(Professor professor) {
        //produtoRepository.recuperarProdutoPorIdComLock(produto.getId());
        professorRepository.findById(professor.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Professor com id = " + professor.getId() + " não encontrado."));
        return professorRepository.save(professor);
    }

    public void removerProfessorPorId(Long id){
        professorRepository.deleteById(id);

    }

    public Professor recuperarProfessorPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Professor com id = " + id + " não encontrado."));
    }


}
