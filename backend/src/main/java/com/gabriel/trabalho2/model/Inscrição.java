package com.gabriel.trabalho2.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Inscrição {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message="Deve ser preenchida uma data e hora para a inscrição.")
    private LocalDate dataHora;


    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message="O aluno deve ser informado.")
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message="A turma deve ser informada.")
    private Turma turma;

    public Inscrição(Turma turma, Aluno aluno, LocalDate dataHora) {
        this.turma = turma;
        this.aluno = aluno;
        this.dataHora = dataHora;
    }
}
