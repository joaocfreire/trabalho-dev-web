package br.uff.ic.controller;

import br.uff.ic.auth.service.JwtService;
import br.uff.ic.auth.service.UsuarioDetailsService;
import br.uff.ic.model.Disciplina;
import br.uff.ic.service.DisciplinaService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DisciplinaController.class)
@AutoConfigureMockMvc(addFilters = false) // Desativa o JWT para testar apenas o Controller
class DisciplinaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DisciplinaService disciplinaService;

    // Mocks do Security para não quebrar o contexto
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Disciplina disciplinaPadrao;

    @BeforeEach
    void setUp() {
        disciplinaPadrao = new Disciplina();
        disciplinaPadrao.setId(1L);
        disciplinaPadrao.setNome("Desenvolvimento Web");
        disciplinaPadrao.setCargaHoraria(60);
    }

    @Test
    @DisplayName("POST /disciplinas - Deve retornar status 200 e a disciplina cadastrada")
    void cadastrarDisciplina_deveRetornarStatus200() throws Exception {
        when(disciplinaService.cadastrarDisciplina(any(Disciplina.class))).thenReturn(disciplinaPadrao);

        mockMvc.perform(post("/disciplinas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(disciplinaPadrao))) // Envia a disciplina como JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Desenvolvimento Web"));
    }
}