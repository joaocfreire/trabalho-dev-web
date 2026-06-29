package br.uff.ic.controller;

import br.uff.ic.auth.service.JwtService;
import br.uff.ic.auth.service.UsuarioDetailsService;
import br.uff.ic.model.Disciplina;
import br.uff.ic.model.Professor;
import br.uff.ic.model.Turma;
import br.uff.ic.service.TurmaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TurmaController.class)
@AutoConfigureMockMvc(addFilters = false) // Desativa a segurança para o teste unitário
class TurmaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TurmaService turmaService;

    // Mocks do Security para permitir o carregamento do contexto Web
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Turma turmaPadrao;

    @BeforeEach
    void setUp() {
        turmaPadrao = new Turma();
        turmaPadrao.setId(1);
        // Preenchimento de campos básicos para evitar potenciais erros 422 de @Valid
        turmaPadrao.setAno("2026");
        turmaPadrao.setPeriodo("1º Período");
        turmaPadrao.setCodigo("T001");

        Professor professor = new Professor();
        professor.setId(1L);
        turmaPadrao.setProfessor(professor);

        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);
        turmaPadrao.setDisciplina(disciplina);
    }

    @Test
    @DisplayName("GET /turmas/{id} - Deve retornar status 200 e a turma correspondente")
    void recuperarTurmaPorId_deveRetornarStatus200() throws Exception {
        when(turmaService.recuperarTurmaPorId(1L)).thenReturn(turmaPadrao);

        mockMvc.perform(get("/turmas/{idTurma}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("T001"));
    }

    @Test
    @DisplayName("POST /turmas - Deve retornar status 200 e a turma cadastrada")
    void cadastrarTurma_deveRetornarStatus200() throws Exception {
        when(turmaService.cadastrarTurma(any(Turma.class))).thenReturn(turmaPadrao);

        mockMvc.perform(post("/turmas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(turmaPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ano").value("2026"));
    }

    @Test
    @DisplayName("DELETE /turmas/{id} - Deve retornar status 200 (OK)")
    void removerTurmaPorId_deveRetornarStatus200() throws Exception {
        mockMvc.perform(delete("/turmas/{idTurma}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /turmas - Deve retornar status 200 e a lista de turmas")
    void recuperarTodasTurmas_deveRetornarStatus200() throws Exception {
        when(turmaService.recuperarTodasTurmas()).thenReturn(List.of(turmaPadrao));

        mockMvc.perform(get("/turmas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].periodo").value("1º Período"));
    }
}