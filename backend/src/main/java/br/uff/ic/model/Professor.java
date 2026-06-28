package br.uff.ic.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message="O nome deve ser informado.")
    private String nome;
    @NotEmpty(message="O email deve ser informado.")
    private String email;

    public Professor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}
