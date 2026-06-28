import type { Professor } from "./Professor";
import type { Disciplina } from "./Disciplina";

export interface Turma {
  id: number;
  ano: number;
  periodo: string;
  disciplina: Disciplina;
  professor: Professor;
  codigo: string;
}
