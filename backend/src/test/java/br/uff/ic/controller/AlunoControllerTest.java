package br.uff.ic.controller;

import br.uff.ic.auth.service.JwtService;
import br.uff.ic.auth.service.UsuarioDetailsService;
import br.uff.ic.model.Aluno;
import br.uff.ic.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlunoController.class) // Levanta apenas o AlunoController
@AutoConfigureMockMvc(addFilters = false) // Desabilita o JWT/Security apenas para este teste unitário
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Objeto responsável por simular as chamadas HTTP (Postman de mentira)

    @MockitoBean
    private AlunoService alunoService; // Substitui o @Mock e @InjectMocks no contexto do Spring

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ObjectMapper objectMapper; // Transforma objetos Java em JSON e vice-versa

    private Aluno alunoPadrao;

    @BeforeEach
    void setUp() {
        alunoPadrao = new Aluno("João", "joao@email.com");
        alunoPadrao.setId(1L);
    }

    @Test
    @DisplayName("GET /alunos - Deve retornar status 200 e a lista de alunos")
    void recuperarAlunos_deveRetornarStatus200() throws Exception {
        when(alunoService.recuperarAlunos()).thenReturn(List.of(alunoPadrao));

        mockMvc.perform(get("/alunos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));
    }

    @Test
    @DisplayName("GET /alunos/{id} - Deve retornar status 200 e o aluno específico")
    void recuperarAlunoPorId_deveRetornarStatus200() throws Exception {
        when(alunoService.recuperarAlunoPorId(1L)).thenReturn(alunoPadrao);

        mockMvc.perform(get("/alunos/{idAluno}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("POST /alunos - Deve retornar status 200 e o aluno cadastrado")
    void cadastrarAluno_deveRetornarStatus200() throws Exception {
        when(alunoService.cadastrarAluno(any(Aluno.class))).thenReturn(alunoPadrao);

        mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoPadrao))) // Converte o alunoPadrao para JSON na requisição
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @DisplayName("PUT /alunos - Deve retornar status 200 e o aluno alterado")
    void alterarAluno_deveRetornarStatus200() throws Exception {
        when(alunoService.alterarAluno(any(Aluno.class))).thenReturn(alunoPadrao);

        mockMvc.perform(put("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alunoPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /alunos/{id} - Deve retornar status 200 (OK)")
    void removerAlunoPorId_deveRetornarStatus200() throws Exception {
        // Como o método retorna void, não precisamos fazer 'when'
        
        mockMvc.perform(delete("/alunos/{idAluno}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}