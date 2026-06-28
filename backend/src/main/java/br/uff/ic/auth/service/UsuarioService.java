package br.uff.ic.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.uff.ic.auth.model.Usuario;
import br.uff.ic.auth.repository.UsuarioRepository;
import br.uff.ic.auth.util.InfoUsuario;
import br.uff.ic.auth.util.Role;

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
