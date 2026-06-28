package br.uff.ic.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.uff.ic.model.Aluno;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByNome(String nome);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Aluno a where a.id= :id")
    Optional<Aluno> recuperarAlunoPorIdComLock(@Param("id") Long id);

    @Query("select a from Aluno a order by a.id")
    List<Aluno> recuperarAlunos();

}
