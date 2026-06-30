package br.uff.ic.integration;

import br.uff.ic.auth.util.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsuarioIntegrationTest extends IntegrationTestBase {

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /usuarios - deve cadastrar novo usuário sem autenticação")
    void cadastrarUsuario_deveRetornarSucesso() throws Exception {
        String body = """
            {
              "nome": "Novo Usuario",
              "email": "novo@test.com",
              "senha": "senha123",
              "role": "USER"
            }
            """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valido").value(true))
            .andExpect(jsonPath("$.mensagem").value("Usuário cadastrado com sucesso!"));
    }

    @Test
    @DisplayName("POST /usuarios - deve impedir cadastro com email duplicado")
    void cadastrarUsuario_emailDuplicado_deveRetornarErro() throws Exception {
        criarUsuario("Existente", "duplicado@test.com", "senha123", Role.USER);

        String body = """
            {
              "nome": "Outro Usuario",
              "email": "duplicado@test.com",
              "senha": "outrasenha",
              "role": "USER"
            }
            """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valido").value(false))
            .andExpect(jsonPath("$.duplicado").value(true))
            .andExpect(jsonPath("$.mensagem").value("Email já cadastrado!"));
    }

    @Test
    @DisplayName("Cadastro seguido de login - fluxo de autenticação completo")
    void cadastroELogin_fluxoCompleto() throws Exception {
        String cadastro = """
            {
              "nome": "Usuario Integracao",
              "email": "integracao@test.com",
              "senha": "minhasenha",
              "role": "USER"
            }
            """;

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cadastro))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valido").value(true));

        String login = objectMapper.writeValueAsString(
            java.util.Map.of("email", "integracao@test.com", "senha", "minhasenha"));

        mockMvc.perform(post("/autenticacao/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(login))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(jsonPath("$.nome").value("Usuario Integracao"));
    }
}
