package br.uff.ic.integration;

import br.uff.ic.auth.util.Role;
import br.uff.ic.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AlunoIntegrationTest extends IntegrationTestBase {

    private String tokenAdmin;
    private String tokenUser;

    @BeforeEach
    void setUp() throws Exception {
        usuarioRepository.deleteAll();
        criarUsuario("Admin", "admin@test.com", "senha123", Role.ADMIN);
        criarUsuario("Usuario", "user@test.com", "senha123", Role.USER);
        tokenAdmin = obterToken("admin@test.com", "senha123");
        tokenUser = obterToken("user@test.com", "senha123");
    }

    @Test
    @DisplayName("GET /alunos - deve ser público e retornar lista vazia inicialmente")
    void recuperarAlunos_semToken_deveRetornar200() throws Exception {
        mockMvc.perform(get("/alunos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("POST /alunos - deve retornar 401 sem token")
    void cadastrarAluno_semToken_deveRetornar401() throws Exception {
        Aluno aluno = new Aluno("Maria Silva", "maria@email.com");

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /alunos - deve retornar 403 para usuário comum")
    void cadastrarAluno_comTokenUser_deveRetornar403() throws Exception {
        Aluno aluno = new Aluno("Maria Silva", "maria@email.com");

        mockMvc.perform(post("/alunos")
                .header("Authorization", "Bearer " + tokenUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /alunos - admin deve cadastrar aluno com sucesso")
    void cadastrarAluno_comTokenAdmin_deveRetornar200() throws Exception {
        Aluno aluno = new Aluno("Maria Silva", "maria@email.com");

        mockMvc.perform(post("/alunos")
                .header("Authorization", "Bearer " + tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Maria Silva"))
            .andExpect(jsonPath("$.email").value("maria@email.com"))
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Fluxo completo - cadastrar, listar e remover aluno")
    void fluxoCompletoAluno() throws Exception {
        Aluno aluno = new Aluno("João Souza", "joao@email.com");

        String response = mockMvc.perform(post("/alunos")
                .header("Authorization", "Bearer " + tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aluno)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/alunos/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("João Souza"));

        mockMvc.perform(delete("/alunos/{id}", id)
                .header("Authorization", "Bearer " + tokenAdmin))
            .andExpect(status().isOk());
    }
}
