package com.gabriel.trabalho2.auth.controller;

import com.gabriel.trabalho2.auth.model.Usuario;
import com.gabriel.trabalho2.auth.service.UsuarioService;
import com.gabriel.trabalho2.auth.util.InfoUsuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
