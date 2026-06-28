package br.uff.ic.service;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Turma;
import br.uff.ic.repository.TurmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @Mock
    private TurmaRepository turmaRepository;

    @InjectMocks
    private TurmaService turmaService;

    private Turma turmaPadrao;

    @BeforeEach
    void setUp() {
        turmaPadrao = new Turma();
        turmaPadrao.setId(1);
    }

    @Test
    @DisplayName("Deve recuperar turma por ID com sucesso")
    void recuperarTurmaPorId_deveRetornarTurmaQuandoExistir() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.of(turmaPadrao));

        Turma resultado = turmaService.recuperarTurmaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    @DisplayName("Deve lançar EntidadeNaoEncontradaException quando turma não existir")
    void recuperarTurmaPorId_deveLancarExcecaoQuandoNaoExistir() {
        when(turmaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> turmaService.recuperarTurmaPorId(1L));
    }

    @Test
    @DisplayName("Deve cadastrar uma nova turma")
    void cadastrarTurma_deveSalvarERetornarTurma() {
        when(turmaRepository.save(any(Turma.class))).thenReturn(turmaPadrao);

        Turma resultado = turmaService.cadastrarTurma(new Turma());

        assertNotNull(resultado);
        verify(turmaRepository, times(1)).save(any(Turma.class));
    }

    @Test
    @DisplayName("Deve remover turma por ID")
    void removerTurmaPorId_deveChamarMetodoDelete() {
        turmaService.removerTurmaPorId(1L);
        verify(turmaRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve recuperar todas as turmas")
    void recuperarTodasTurmas_deveRetornarListaDeTurmas() {
        when(turmaRepository.findAll()).thenReturn(List.of(turmaPadrao));

        List<Turma> resultado = turmaService.recuperarTodasTurmas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
}