package com.gabriel.trabalho2.auth.util;

public record TokenResponse(String token, long idUsuario, String nome, String role) {
}

