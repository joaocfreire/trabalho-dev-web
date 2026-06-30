package br.uff.ic;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import br.uff.ic.auth.model.Usuario;
import br.uff.ic.auth.service.UsuarioService;
import br.uff.ic.auth.util.InfoUsuario;
import br.uff.ic.auth.util.Role;
import br.uff.ic.model.*;
import br.uff.ic.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AlunoRepository alunoRepository;
    private final InscricaoRepository inscricaoRepository;
    private final TurmaRepository turmaRepository;  
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        
        InfoUsuario admin = usuarioService.cadastrarUsuario(new Usuario("Administrador José", "admin@admin.com", "admin", Role.ADMIN));
        InfoUsuario user = usuarioService.cadastrarUsuario(new Usuario("Usuário Pedro", "user@user.com", "user", Role.USER));

        // --- 1. CONFIGURAÇÃO (Disciplinas e Professores) ---
        Disciplina grafos = disciplinaRepository.save(new Disciplina("Algoritmos em Gráfos", 64));
        Disciplina devWeb = disciplinaRepository.save(new Disciplina("Desenvolvimento Web", 64));
        Disciplina redes2 = disciplinaRepository.save(new Disciplina("Redes de Computadores 2", 64));
        Disciplina sd = disciplinaRepository.save(new Disciplina("Sistemas Distribuídos", 64));

        Professor carlos = professorRepository.save(new Professor("Carlos","carlos@ic.uff.br"));
        Professor luisfelipe = professorRepository.save(new Professor("Luís Felipe","lf@ic.uff.br"));
        Professor celio = professorRepository.save(new Professor("Célio", "celio@ic.uff.br"));
        Professor flavia = professorRepository.save(new Professor("Flávia", "flavia@ic.uff.br"));

        // --- 2. CRIAÇÃO DAS TURMAS ---
        Turma turma1 = turmaRepository.save(new Turma("2026","1° Semestre",carlos, devWeb, "DW001"));
        Turma turma2 = turmaRepository.save(new Turma("2026","1° Semestre",luisfelipe, grafos, "AG001"));
        Turma turma3 = turmaRepository.save(new Turma("2026","1 ° Semestre", celio, redes2,"RD001"));
        Turma turma4 = turmaRepository.save(new Turma("2026","1° Semestre",carlos, devWeb, "DW002"));
        Turma turma5 = turmaRepository.save(new Turma("2026","1° Semestre",flavia, sd, "SD001"));

        List<Turma> turmas = List.of(turma1, turma2, turma3, turma4, turma5);
        final int NUM_TURMAS = turmas.size();

        // --- 3. ALUNOS ORIGINAIS (Armazenados para uso posterior) ---
        List<Aluno> todosAlunos = new ArrayList<>();

        Aluno ana = alunoRepository.save(new Aluno("Ana","anarebello@gmail.com"));
        todosAlunos.add(ana);
        Aluno bruno = alunoRepository.save(new Aluno("Bruno","brunomendes@gmail.com"));
        todosAlunos.add(bruno);
        Aluno carla = alunoRepository.save(new Aluno("Carla","carlalopes@gmail.com"));
        todosAlunos.add(carla);
        Aluno diego = alunoRepository.save(new Aluno("Diego","diegoalmeida@gmail.com"));
        todosAlunos.add(diego);
        Aluno elaine = alunoRepository.save(new Aluno("Elaine","elainelopes@gmail.com"));
        todosAlunos.add(elaine);

        // --- 4. GERAÇÃO E SALVAMENTO DOS 20 ALUNOS COM NOMES DE PESSOAS ---
        List<String> novosNomes = List.of(
                "Ricardo Silva", "Mariana Santos", "Pedro Almeida", "Juliana Costa",
                "Lucas Pereira", "Beatriz Oliveira", "Felipe Souza", "Gabriela Fernandes",
                "Rafael Martins", "Larissa Rocha", "Thiago Nogueira", "Camila Viana",
                "Daniel Lemos", "Vitória Gomes", "Guilherme Reis", "Amanda Carvalho",
                "Eduardo Pires", "Isabela Borges", "Jonas Freire", "Laura Mendes"
        );

        for (String nome : novosNomes) {
            String[] partesNome = nome.toLowerCase().split(" ");
            String email = partesNome[0] + "." + partesNome[1] + "@ic.br";
            Aluno novoAluno = alunoRepository.save(new Aluno(nome, email));
            todosAlunos.add(novoAluno);
        }

        final int TOTAL_ALUNOS = todosAlunos.size();
        LocalDate dataInscricaoBase = LocalDate.of(2025, 11, 1);

        // --- 5. INSCRIÇÃO ÚNICA: T1=22, T2=11 ---
        final int ALUNOS_DESEJADOS_TURMA1 = 22;

        for (int i = 0; i < ALUNOS_DESEJADOS_TURMA1; i++) {
            Aluno alunoAtual = todosAlunos.get(i);
            inscricaoRepository.save(new Inscrição(turma1, alunoAtual, dataInscricaoBase.plusDays(i)));
        }

        for (int i = 0; i < TOTAL_ALUNOS; i++) {
            Aluno alunoAtual = todosAlunos.get(i);
            Turma turmaParaInscrever;

            if (i == 2 || i == 4) {
                turmaParaInscrever = turma2;
            } else {
                int turmaIndex = (i % (NUM_TURMAS - 1)) + 1;
                turmaParaInscrever = turmas.get(turmaIndex);
            }

            inscricaoRepository.save(new Inscrição(turmaParaInscrever, alunoAtual, dataInscricaoBase.plusDays(i + 100)));
        }
    }
}