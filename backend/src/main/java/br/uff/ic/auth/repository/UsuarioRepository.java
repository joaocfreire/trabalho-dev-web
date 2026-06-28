package br.uff.ic.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uff.ic.auth.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
