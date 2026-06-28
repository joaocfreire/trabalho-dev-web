package br.uff.ic.service;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Aluno;
import br.uff.ic.repository.AlunoRepository;
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

@ExtendWith(MockitoExtension.class) // Habilita o uso do Mockito nesta classe
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository; // Cria um mock (dublê) do repositório

    @InjectMocks
    private AlunoService alunoService; // Injeta o mock do repositório dentro do serviço real

    private Aluno alunoPadrao;

    @BeforeEach
    void setUp() {
        // Executado antes de cada teste para garantir um estado limpo
        alunoPadrao = new Aluno("João", "joao@email.com");
        alunoPadrao.setId(1L);
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os alunos")
    void recuperarAlunos_deveRetornarListaDeAlunos() {
        // Arrange: Quando o repositório for chamado, retorne nossa lista fake
        when(alunoRepository.findAll()).thenReturn(List.of(alunoPadrao));

        // Act: Executamos o método do serviço
        List<Aluno> resultado = alunoService.recuperarAlunos();

        // Assert: Verificamos se o resultado foi o esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
    }

    @Test
    @DisplayName("Deve salvar um novo aluno com sucesso")
    void cadastrarAluno_deveSalvarERetornarAluno() {
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoPadrao);

        Aluno resultado = alunoService.cadastrarAluno(new Aluno("João", "joao@email.com"));

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        // Verifica se o método save do repositório foi chamado exatamente 1 vez
        verify(alunoRepository, times(1)).save(any(Aluno.class)); 
    }

    @Test
    @DisplayName("Deve alterar um aluno que já existe (usando lock)")
    void alterarAluno_deveAlterarERetornarAlunoQuandoExistir() {
        // Simulando que o aluno foi encontrado com lock
        when(alunoRepository.recuperarAlunoPorIdComLock(1L)).thenReturn(Optional.of(alunoPadrao));
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoPadrao);

        Aluno resultado = alunoService.alterarAluno(alunoPadrao);

        assertNotNull(resultado);
        verify(alunoRepository, times(1)).save(alunoPadrao);
    }

    @Test
    @DisplayName("Deve lançar EntidadeNaoEncontradaException ao tentar alterar aluno inexistente")
    void alterarAluno_deveLancarExcecaoQuandoAlunoNaoExistir() {
        // Simulando que o aluno NÃO foi encontrado (retorna Optional vazio)
        when(alunoRepository.recuperarAlunoPorIdComLock(1L)).thenReturn(Optional.empty());

        // Verificamos se a exceção correta foi lançada
        assertThrows(EntidadeNaoEncontradaException.class, () -> alunoService.alterarAluno(alunoPadrao));
        
        // Garantimos que o save NUNCA foi chamado já que deu erro antes
        verify(alunoRepository, never()).save(any(Aluno.class));
    }

    @Test
    @DisplayName("Deve chamar o método deleteById do repositório")
    void removerAlunoPorId_deveChamarMetodoDelete() {
        // Como o método retorna void, só precisamos chamar e verificar a interação
        alunoService.removerAlunoPorId(1L);

        verify(alunoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Deve recuperar um aluno por ID com sucesso")
    void recuperarAlunoPorId_deveRetornarAlunoQuandoExistir() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(alunoPadrao));

        Aluno resultado = alunoService.recuperarAlunoPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("joao@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar aluno por ID que não existe")
    void recuperarAlunoPorId_deveLancarExcecaoQuandoNaoExistir() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> alunoService.recuperarAlunoPorId(1L));
    }
}