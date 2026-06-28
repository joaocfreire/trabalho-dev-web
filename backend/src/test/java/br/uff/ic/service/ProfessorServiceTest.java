package br.uff.ic.service;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Professor;
import br.uff.ic.repository.ProfessorRepository;
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
class ProfessorServiceTest {

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    private Professor professorPadrao;

    @BeforeEach
    void setUp() {
        professorPadrao = new Professor("Maria", "maria@email.com");
        professorPadrao.setId(1L);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os professores")
    void recuperarProfessores_deveRetornarListaDeProfessores() {
        when(professorRepository.findAll()).thenReturn(List.of(professorPadrao));

        List<Professor> resultado = professorService.recuperarTodosProfessores();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Maria", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve salvar um novo professor com sucesso")
    void cadastrarProfessor_deveSalvarERetornarProfessor() {
        when(professorRepository.save(any(Professor.class))).thenReturn(professorPadrao);

        Professor resultado = professorService.cadastrarProfessor(new Professor("Maria", "maria@email.com"));

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(professorRepository, times(1)).save(any(Professor.class));
    }

    @Test
    @DisplayName("Deve alterar um professor que já existe (usando lock)")
    void alterarProfessor_deveAlterarERetornarProfessorQuandoExistir() {
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professorPadrao));
        when(professorRepository.save(any(Professor.class))).thenReturn(professorPadrao);

        Professor resultado = professorService.alterarProfessor(professorPadrao);

        assertNotNull(resultado);
        verify(professorRepository, times(1)).save(professorPadrao);
    }

    @Test
    @DisplayName("Deve lançar EntidadeNaoEncontradaException ao tentar alterar professor inexistente")
    void alterarProfessor_deveLancarExcecaoQuandoProfessorNaoExistir() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> professorService.alterarProfessor(professorPadrao));
        
        // Garante que não salvou se o professor não foi encontrado no banco
        verify(professorRepository, never()).save(any(Professor.class));
    }

    @Test
    @DisplayName("Deve chamar o método deleteById do repositório")
    void removerProfessorPorId_deveChamarMetodoDelete() {
        professorService.removerProfessorPorId(1L);

        verify(professorRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve recuperar um professor por ID com sucesso")
    void recuperarProfessorPorId_deveRetornarProfessorQuandoExistir() {
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professorPadrao));

        Professor resultado = professorService.recuperarProfessorPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("maria@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar professor por ID que não existe")
    void recuperarProfessorPorId_deveLancarExcecaoQuandoNaoExistir() {
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> professorService.recuperarProfessorPorId(1L));
    }
}