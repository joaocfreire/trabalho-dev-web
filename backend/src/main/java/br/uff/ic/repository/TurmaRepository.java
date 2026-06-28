package br.uff.ic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uff.ic.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma,Long> {
}
