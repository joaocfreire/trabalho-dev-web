package br.uff.ic.controller;

import br.uff.ic.auth.service.JwtService;
import br.uff.ic.auth.service.UsuarioDetailsService;
import br.uff.ic.model.Professor;
import br.uff.ic.repository.ProfessorRepository;
import br.uff.ic.service.ProfessorService;
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

@WebMvcTest(ProfessorController.class)
@AutoConfigureMockMvc(addFilters = false) // Desativa a segurança para o teste
class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfessorService professorService; // Mock do serviço específico

    @MockitoBean
    private ProfessorRepository professorRepository;
    
    // Mocks necessários para o Spring Security instanciar o contexto web
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Professor professorPadrao;

    @BeforeEach
    void setUp() {
        professorPadrao = new Professor("Maria", "maria@ic.uff.br");
        professorPadrao.setId(1L);
    }

    @Test
    @DisplayName("GET /professores - Deve retornar status 200 e a lista")
    void recuperarProfessores_deveRetornarStatus200() throws Exception {
        when(professorService.recuperarTodosProfessores()).thenReturn(List.of(professorPadrao));

        mockMvc.perform(get("/professores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Maria"));
    }

    @Test
    @DisplayName("GET /professores/{id} - Deve retornar status 200 e o professor")
    void recuperarProfessorPorId_deveRetornarStatus200() throws Exception {
        when(professorService.recuperarProfessorPorId(1L)).thenReturn(professorPadrao);

        mockMvc.perform(get("/professores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    @DisplayName("POST /professores - Deve retornar status 200 e o professor cadastrado")
    void cadastrarProfessor_deveRetornarStatus200() throws Exception {
        when(professorService.cadastrarProfessor(any(Professor.class))).thenReturn(professorPadrao);

        mockMvc.perform(post("/professores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("PUT /professores - Deve retornar status 200 e o professor alterado")
    void alterarProfessor_deveRetornarStatus200() throws Exception {
        when(professorService.alterarProfessor(any(Professor.class))).thenReturn(professorPadrao);

        mockMvc.perform(put("/professores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(professorPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /professores/{id} - Deve retornar status 200")
    void removerProfessorPorId_deveRetornarStatus200() throws Exception {
        mockMvc.perform(delete("/professores/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}