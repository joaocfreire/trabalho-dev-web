import type { Aluno } from "./Aluno";
import type { Turma } from "./Turma";

export interface Inscricao {
  id?: number;
  dataHora: string;
  aluno: Aluno;
  turma: Turma;
}
