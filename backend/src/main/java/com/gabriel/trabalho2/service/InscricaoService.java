package com.gabriel.trabalho2.service;

import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Inscrição;
import com.gabriel.trabalho2.repository.InscricaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;

    public InscricaoService(InscricaoRepository inscricaoRepository) {this.inscricaoRepository = inscricaoRepository;}

    public Inscrição cadastrarInscricao(Inscrição inscricao){
        System.out.println(inscricao.getAluno().getId());
        System.out.println(inscricao.getTurma().getId());
        return inscricaoRepository.save(inscricao);
    }

    public void removerInscricaoPorId(Long id){inscricaoRepository.deleteById(id);}

    public List<Inscrição> recuperarInscricoes() {
        return inscricaoRepository.findAll();
    }
    public List<Inscrição> recuperarInscricoesPorTurma(Long id){
        return inscricaoRepository.recuperarInscricoesPorTurma(id);
    }
    public Page<Inscrição> recuperarInscricoesPorTurmaComPaginacao(Pageable pageable, Long id){
        return inscricaoRepository.recuperarInscricoesPorTurmaComPaginacao(pageable, id);
    }


}
