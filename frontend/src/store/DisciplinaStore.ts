import { create } from "zustand";
import type { Disciplina } from "../interfaces/Disciplina";

interface DisciplinaStore {
    pagina: number;
    tamanho: number;
    nome: string;
    mensagem: string;
    disciplinaSelecionado: Disciplina;

    setPagina: (novaPagina: number) => void;
    setNome: (novoNome: string) => void;
    setMensagem: (novaMensagem: string) => void;
    setDisciplinaSelecionado: (novoDisciplinaSelecionado: Disciplina) => void;
}

const useDisciplinaStore = create<DisciplinaStore>((set) => ({
    pagina: 0,
    tamanho: 5,
    nome: "",
    mensagem: "",
    disciplinaSelecionado: {} as Disciplina,

    setPagina: (novaPagina: number) => set(() => ({pagina: novaPagina})),
    setNome: (novoNome: string) => set(() => ({nome: novoNome})),
    setMensagem: (novaMensagem: string) => set(() => ({mensagem: novaMensagem})),
    setDisciplinaSelecionado: (novoDisciplinaSelecionado:Disciplina) => set(() => ({disciplinaSelecionado: novoDisciplinaSelecionado})),
}))
export default useDisciplinaStore;