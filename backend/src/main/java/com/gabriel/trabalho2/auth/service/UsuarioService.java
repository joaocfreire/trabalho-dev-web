package com.gabriel.trabalho2.auth.service;

import com.gabriel.trabalho2.auth.model.Usuario;
import com.gabriel.trabalho2.auth.repository.UsuarioRepository;
import com.gabriel.trabalho2.auth.util.InfoUsuario;
import com.gabriel.trabalho2.auth.util.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public InfoUsuario cadastrarUsuario(Usuario usuario) {
        try {
            Usuario usuarioCadastrado = usuarioRepository
                .findByEmail(usuario.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
            return new InfoUsuario(false, true, "Email já cadastrado!");
        }
        catch(UsernameNotFoundException e) {
            System.out.println(">>>>>>>>>>>>> usuario.getSenha() = " + usuario.getSenha());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            //usuario.setRole(Role.USER);
            usuarioRepository.save(usuario);
            return new InfoUsuario(true, false, "Usuário cadastrado com sucesso!");
        }
    }

    public List<Usuario> recuperarUsuarios() {
        return usuarioRepository.findAll();
    }
}
