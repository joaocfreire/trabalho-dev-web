import type { Turma } from "../interfaces/Turma";

export class Formatters {
  static getFullTurmaNome(turma: Turma): string {
    return (
      turma.codigo +
      " - " +
      turma.disciplina.nome +
      " do " +
      turma.periodo +
      " de " +
      turma.ano
    );
  }
}
