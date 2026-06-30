package br.uff.ic.integration;

import br.uff.ic.auth.util.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /autenticacao/login - deve retornar token JWT com credenciais válidas")
    void login_comCredenciaisValidas_deveRetornarToken() throws Exception {
        criarUsuario("Admin Teste", "admin@test.com", "senha123", Role.ADMIN);

        String body = objectMapper.writeValueAsString(
            java.util.Map.of("email", "admin@test.com", "senha", "senha123"));

        mockMvc.perform(post("/autenticacao/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(jsonPath("$.nome").value("Admin Teste"))
            .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @DisplayName("POST /autenticacao/login - deve retornar 401 com senha inválida")
    void login_comSenhaInvalida_deveRetornar401() throws Exception {
        criarUsuario("Usuario Teste", "user@test.com", "senha123", Role.USER);

        String body = objectMapper.writeValueAsString(
            java.util.Map.of("email", "user@test.com", "senha", "senha_errada"));

        mockMvc.perform(post("/autenticacao/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /turmas - deve retornar 401 sem autenticação")
    void turmas_semToken_deveRetornar401() throws Exception {
        mockMvc.perform(get("/turmas"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /turmas - deve retornar 200 com token válido")
    void turmas_comToken_deveRetornar200() throws Exception {
        criarUsuario("Usuario Teste", "user@test.com", "senha123", Role.USER);
        String token = obterToken("user@test.com", "senha123");

        mockMvc.perform(get("/turmas")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
    }
}
