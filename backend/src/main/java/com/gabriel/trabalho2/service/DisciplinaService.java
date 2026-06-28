package com.gabriel.trabalho2.service;

import com.gabriel.trabalho2.model.Disciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabriel.trabalho2.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Disciplina cadastrarDisciplina(Disciplina disciplina){
        return disciplinaRepository.save(disciplina);
    }

}
