package com.gabriel.trabalho2;

import com.gabriel.trabalho2.auth.model.Usuario;
import com.gabriel.trabalho2.auth.repository.UsuarioRepository;
import com.gabriel.trabalho2.auth.service.UsuarioService;
import com.gabriel.trabalho2.auth.util.InfoUsuario;
import com.gabriel.trabalho2.auth.util.Role;
import com.gabriel.trabalho2.model.*;
import com.gabriel.trabalho2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class Trabalho2Application implements CommandLineRunner {

    private final AlunoRepository alunoRepository;
    private final InscricaoRepository inscricaoRepository;
    private final TurmaRepository turmaRepository;
    private final ProfessorRepository professorRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final UsuarioService usuarioService;


    public static void main(String[] args) {
        SpringApplication.run(Trabalho2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        InfoUsuario admin = usuarioService.cadastrarUsuario(new Usuario("Administrador José", "admin@admin.com", "admin", Role.ADMIN));
        InfoUsuario user = usuarioService.cadastrarUsuario(new Usuario("Usuário Genival", "user@user.com", "user", Role.USER));

        // --- 1. CONFIGURAÇÃO (Disciplinas e Professores) ---
        Disciplina matDis = disciplinaRepository.save(new Disciplina("Matemática Discreta", 64));
        Disciplina devWeb = disciplinaRepository.save(new Disciplina("Desenvolvimento Web", 64));
        Disciplina c2 = disciplinaRepository.save(new Disciplina("Cálculo Segundo", 64));

        Professor carlos = professorRepository.save(new Professor("Carlos","carlos@ic.uff.br"));
        Professor denise = professorRepository.save(new Professor("Denise","denise@ic.uff.br"));
        Professor joana = professorRepository.save(new Professor("Joana", "joana@ic.uff.br"));

        // --- 2. CRIAÇÃO DAS TURMAS ---
        Turma turma1 = turmaRepository.save(new Turma("2025","1° Semestre",carlos, devWeb, "D001"));
        Turma turma2 = turmaRepository.save(new Turma("2025","2° Semestre",denise, c2, "C001"));
        Turma turma3 = turmaRepository.save(new Turma("2026","1 ° Semestre",joana, matDis,"M001"));
        Turma turma4 = turmaRepository.save(new Turma("2026","2° Semestre",carlos, devWeb, "D002"));

        List<Turma> turmas = List.of(turma1, turma2, turma3, turma4);
        final int NUM_TURMAS = turmas.size();

        // --- 3. ALUNOS ORIGINAIS (Armazenados para uso posterior) ---
        List<Aluno> todosAlunos = new ArrayList<>();

        Aluno ana = alunoRepository.save(new Aluno("Ana","anarebello@gmail.com"));
        todosAlunos.add(ana); // Índice 0
        Aluno bruno = alunoRepository.save(new Aluno("Bruno","brunomendes@gmail.com"));
        todosAlunos.add(bruno); // Índice 1
        Aluno carla = alunoRepository.save(new Aluno("Carla","carlalopes@gmail.com"));
        todosAlunos.add(carla); // Índice 2
        Aluno diego = alunoRepository.save(new Aluno("Diego","diegoalmeida@gmail.com"));
        todosAlunos.add(diego); // Índice 3
        Aluno elaine = alunoRepository.save(new Aluno("Elaine","elainelopes@gmail.com"));
        todosAlunos.add(elaine); // Índice 4

        // -----------------------------------------------------------------
        //  4. GERAÇÃO E SALVAMENTO DOS 20 ALUNOS COM NOMES DE PESSOAS
        // -----------------------------------------------------------------

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

        // Total de alunos únicos: 25
        final int TOTAL_ALUNOS = todosAlunos.size();

        // -----------------------------------------------------------------
        //  5. INSCRIÇÃO ÚNICA: T1=22, T2=11
        // -----------------------------------------------------------------

        LocalDate dataInscricaoBase = LocalDate.of(2025, 11, 1);

        // --- PASSO 1: INSCREVER OS 22 ALUNOS NA TURMA 1 ---
        final int ALUNOS_DESEJADOS_TURMA1 = 22;

        for (int i = 0; i < ALUNOS_DESEJADOS_TURMA1; i++) {
            Aluno alunoAtual = todosAlunos.get(i);
            inscricaoRepository.save(new Inscrição(turma1, alunoAtual, dataInscricaoBase.plusDays(i)));
        }

        // Turma 1 tem 22 alunos únicos (índices 0 a 21).

        // --- PASSO 2: INSCRIÇÃO CÍCLICA E AJUSTADA NAS TURMAS 2, 3 E 4 ---

        // Turma 2 deve ter 11 alunos. Ela receberia 9 no ciclo normal (i % 3 == 0).
        // Os alunos de índice 2 e 4 (Carla e Elaine) seriam para T4 e T3, respectivamente.
        // Vamos forçá-los para T2 para chegar a 11.

        for (int i = 0; i < TOTAL_ALUNOS; i++) {
            Aluno alunoAtual = todosAlunos.get(i);

            Turma turmaParaInscrever;

            //  LÓGICA DE AJUSTE PARA T2 = 11 ALUNOS (SEM REPETIÇÃO)
            if (i == 2 || i == 4) {
                // Alunos Carla (2) e Elaine (4) serão forçados para a Turma 2.
                turmaParaInscrever = turma2;
            } else {
                // Outros alunos continuam com a distribuição cíclica padrão
                // T2 (i%3=0), T3 (i%3=1), T4 (i%3=2)
                int turmaIndex = (i % (NUM_TURMAS - 1)) + 1; // +1 garante que começamos na Turma 2 (índice 1)
                turmaParaInscrever = turmas.get(turmaIndex);
            }



            inscricaoRepository.save(new Inscrição(turmaParaInscrever, alunoAtual, dataInscricaoBase.plusDays(i + 100)));
        }

    }
}