package com.gabriel.trabalho2.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="O nome do aluno deve ser informado.")
    private String nome;
    @NotEmpty(message="O nome do aluno deve ser informado.")
    private String email;

    public Aluno(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

}
