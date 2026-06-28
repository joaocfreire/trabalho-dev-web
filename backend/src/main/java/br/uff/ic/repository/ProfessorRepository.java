package br.uff.ic.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.uff.ic.model.Aluno;
import br.uff.ic.model.Professor;

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
