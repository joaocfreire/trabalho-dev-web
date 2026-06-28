package br.uff.ic.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import br.uff.ic.exception.EntidadeNaoEncontradaException;
import br.uff.ic.model.Aluno;
import br.uff.ic.repository.AlunoRepository;

import java.util.List;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> recuperarAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno cadastrarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno alterarAluno(Aluno aluno) {
        // Este método precisa ser transacional uma vez que o método abaixo
        // efetua um lock na linha da tabela referente ao produto recuperado.
        // Observe que o método recuperarProdutoPorIdComLock retorna um
        // Optional<Produto>
        alunoRepository.recuperarAlunoPorIdComLock(aluno.getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Aluno com id = " + aluno.getId() + " não encontrado."));
        return alunoRepository.save(aluno);
    }

    public void removerAlunoPorId(Long id){
        alunoRepository.deleteById(id);

    }

    public Aluno recuperarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Aluno com id = " + id + " não encontrado."));
    }


}
