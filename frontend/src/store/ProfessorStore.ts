import { create } from "zustand";
import type { Professor } from "../interfaces/Professor";

interface ProfessorStore {
    pagina: number;
    tamanho: number;
    nome: string;
    mensagem: string;
    professorSelecionado: Professor;

    setPagina: (novaPagina: number) => void;
    setNome: (novoNome: string) => void;
    setMensagem: (novaMensagem: string) => void;
    setProfessorSelecionado: (novoProfessorSelecionado: Professor) => void;
}

const useProfessorStore = create<ProfessorStore>((set) => ({
    pagina: 0,
    tamanho: 5,
    nome: "",
    mensagem: "",
    professorSelecionado: {} as Professor,

    setPagina: (novaPagina: number) => set(() => ({pagina: novaPagina})),
    setNome: (novoNome: string) => set(() => ({nome: novoNome})),
    setMensagem: (novaMensagem: string) => set(() => ({mensagem: novaMensagem})),
    setProfessorSelecionado: (novoProfessorSelecionado:Professor) => set(() => ({professorSelecionado: novoProfessorSelecionado})),
}))
export default useProfessorStore;