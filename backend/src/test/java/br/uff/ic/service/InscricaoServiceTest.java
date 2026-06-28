package br.uff.ic.service;

import br.uff.ic.model.Aluno;
import br.uff.ic.model.Inscrição;
import br.uff.ic.model.Turma;
import br.uff.ic.repository.InscricaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @Mock
    private InscricaoRepository inscricaoRepository;

    @InjectMocks
    private InscricaoService inscricaoService;

    private Inscrição inscricaoPadrao;

    @BeforeEach
    void setUp() {
        inscricaoPadrao = new Inscrição();
        inscricaoPadrao.setId(1);
        
        Aluno aluno = new Aluno();
        aluno.setId(10L);
        Turma turma = new Turma();
        turma.setId(20);
        
        inscricaoPadrao.setAluno(aluno);
        inscricaoPadrao.setTurma(turma);
    }

    @Test
    @DisplayName("Deve cadastrar uma nova inscrição")
    void cadastrarInscricao_deveSalvarERetornarInscricao() {
        when(inscricaoRepository.save(any(Inscrição.class))).thenReturn(inscricaoPadrao);

        Inscrição resultado = inscricaoService.cadastrarInscricao(inscricaoPadrao);

        assertNotNull(resultado);
        verify(inscricaoRepository, times(1)).save(inscricaoPadrao);
    }

    @Test
    @DisplayName("Deve remover inscrição por ID")
    void removerInscricaoPorId_deveChamarMetodoDelete() {
        inscricaoService.removerInscricaoPorId(1L);
        verify(inscricaoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve recuperar todas as inscrições")
    void recuperarInscricoes_deveRetornarLista() {
        when(inscricaoRepository.findAll()).thenReturn(List.of(inscricaoPadrao));

        List<Inscrição> resultado = inscricaoService.recuperarInscricoes();

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve recuperar inscrições por ID da turma")
    void recuperarInscricoesPorTurma_deveRetornarLista() {
        when(inscricaoRepository.recuperarInscricoesPorTurma(20L)).thenReturn(List.of(inscricaoPadrao));

        List<Inscrição> resultado = inscricaoService.recuperarInscricoesPorTurma(20L);

        assertEquals(1, resultado.size());
    }

    @Test
    @DisplayName("Deve recuperar inscrições por turma com paginação")
    void recuperarInscricoesPorTurmaComPaginacao_deveRetornarPagina() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Inscrição> paginaMock = new PageImpl<>(List.of(inscricaoPadrao));

        when(inscricaoRepository.recuperarInscricoesPorTurmaComPaginacao(eq(pageable), eq(20L)))
                .thenReturn(paginaMock);

        Page<Inscrição> resultado = inscricaoService.recuperarInscricoesPorTurmaComPaginacao(pageable, 20L);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
    }
}