package com.gabriel.trabalho2.repository;

import com.gabriel.trabalho2.model.Aluno;
import com.gabriel.trabalho2.model.Professor;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Aluno> findByNome(String nome);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Professor p where p.id= :id")
    Optional<Professor> recuperarProfessorPorIdComLock(@Param("id") Long id);

    @Query("select p from Professor p order by p.id")
    List<Aluno> recuperarTodosProfessores();

}
