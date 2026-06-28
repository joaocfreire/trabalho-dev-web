package br.uff.ic.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import br.uff.ic.auth.model.Usuario;
import br.uff.ic.auth.service.UsuarioService;
import br.uff.ic.auth.util.InfoUsuario;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("usuarios")   // htttp://localhost:8080/usuarios
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> recuperaUsuarios() {
        return usuarioService.recuperarUsuarios();
    }

    @PostMapping
    public InfoUsuario cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario);
    }
}
