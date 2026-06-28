package com.gabriel.trabalho2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Turma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message="O código da turma deve ser informado.")
    private String codigo;
    @NotEmpty(message="O ano deve ser informado.")
    private String ano;
    @NotEmpty(message="O período deve ser informado.")
    private String periodo;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull(message="O professor deve ser informado.")
    private Professor professor;

    @ManyToOne // eager é o default
    @NotNull(message="A disciplina deve ser informada.")
    private Disciplina disciplina;

    public Turma(String ano, String periodo, Professor professor, Disciplina disciplina, String codigo) {
        this.ano = ano;
        this.periodo = periodo;
        this.professor = professor;
        this.disciplina = disciplina;
        this.codigo = codigo;
    }
}
