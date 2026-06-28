package br.uff.ic.service;

import br.uff.ic.model.Disciplina;
import br.uff.ic.repository.DisciplinaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    @Test
    @DisplayName("Deve cadastrar uma nova disciplina com sucesso")
    void cadastrarDisciplina_deveSalvarERetornarDisciplina() {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setNome("Desenvolvimento Web");

        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(disciplina);

        Disciplina resultado = disciplinaService.cadastrarDisciplina(new Disciplina());

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Desenvolvimento Web", resultado.getNome());
        verify(disciplinaRepository, times(1)).save(any(Disciplina.class));
    }
}