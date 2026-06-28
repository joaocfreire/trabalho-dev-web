package com.gabriel.trabalho2.repository;

import com.gabriel.trabalho2.model.Inscrição;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscrição, Long> {
    @Query("select i from Inscrição i where i.turma.id = :id")
    public List<Inscrição> recuperarInscricoesPorTurma(@Param("id")Long id);
    @Query(value="select i from Inscrição i where i.turma.id = :id order by i.aluno.nome", countQuery = "select count(i) from Inscrição i where i.turma.id = :id")
    Page<Inscrição> recuperarInscricoesPorTurmaComPaginacao(Pageable pageable, @Param("id")Long id);
}
