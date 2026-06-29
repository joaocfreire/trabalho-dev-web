package br.uff.ic.controller;

import br.uff.ic.auth.service.JwtService;
import br.uff.ic.auth.service.UsuarioDetailsService;
import br.uff.ic.model.Aluno;
import br.uff.ic.model.Inscrição;
import br.uff.ic.model.Turma;
import br.uff.ic.service.InscricaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InscricaoController.class)
@AutoConfigureMockMvc(addFilters = false) // Desativa o JWT para testar a API
class InscricaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InscricaoService inscricaoService;

    // Mocks do Security
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Inscrição inscricaoPadrao;

    @BeforeEach
    void setUp() {
        inscricaoPadrao = new Inscrição();
        inscricaoPadrao.setId(1);
        inscricaoPadrao.setDataHora(java.time.LocalDate.now());

        // Preenchimento de campos obrigatórios para evitar erro 422 (@Valid)
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        inscricaoPadrao.setAluno(aluno);

        Turma turma = new Turma();
        turma.setId(1);
        inscricaoPadrao.setTurma(turma);
    }

    @Test
    @DisplayName("POST /inscricoes - Deve retornar status 200 e a inscrição cadastrada")
    void cadastrarInscricao_deveRetornarStatus200() throws Exception {
        when(inscricaoService.cadastrarInscricao(any(Inscrição.class))).thenReturn(inscricaoPadrao);

        mockMvc.perform(post("/inscricoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inscricaoPadrao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /inscricoes/{id} - Deve retornar status 200 (OK)")
    void removerInscricaoPorId_deveRetornarStatus200() throws Exception {
        mockMvc.perform(delete("/inscricoes/{idInscricao}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /inscricoes - Deve retornar status 200 e a lista de inscrições")
    void recuperarInscricoes_deveRetornarStatus200() throws Exception {
        when(inscricaoService.recuperarInscricoes()).thenReturn(List.of(inscricaoPadrao));

        mockMvc.perform(get("/inscricoes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /inscricoes/turma={idTurma} - Deve retornar status 200 e inscrições da turma")
    void recuperarInscricoesPorTurma_deveRetornarStatus200() throws Exception {
        when(inscricaoService.recuperarInscricoesPorTurma(1L)).thenReturn(List.of(inscricaoPadrao));

        mockMvc.perform(get("/inscricoes/turma={idTurma}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("GET /inscricoes/paginacao - Deve retornar status 200 e página de inscrições")
    void recuperarInscricoesPaginacao_deveRetornarStatus200() throws Exception {
        // Criar uma página simulada para o mock
        Page<Inscrição> paginaMock = new PageImpl<>(List.of(inscricaoPadrao), PageRequest.of(0, 5), 1);
        
        // Simular a resposta do serviço
        when(inscricaoService.recuperarInscricoesPorTurmaComPaginacao(any(PageRequest.class), eq(1L)))
                .thenReturn(paginaMock);

        // A chamada passa os query parameters (pagina, tamanho, turma)
        mockMvc.perform(get("/inscricoes/paginacao")
                .param("pagina", "0")
                .param("tamanho", "5")
                .param("turma", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}