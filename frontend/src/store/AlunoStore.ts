import { create } from "zustand";
import type { Aluno } from "../interfaces/Aluno";

interface AlunoStore {
    pagina: number;
    tamanho: number;
    nome: string;
    mensagem: string;
    alunoSelecionado: Aluno;
    alunosFiltrados:Aluno[],

    setPagina: (novaPagina: number) => void;
    setNome: (novoNome: string) => void;
    setMensagem: (novaMensagem: string) => void;
    setAlunoSelecionado: (novoAlunoSelecionado: Aluno) => void;
    setAlunosFiltrados: (novosAlunosFiltrados: Aluno[]) => void;

}

const useAlunoStore = create<AlunoStore>((set) => ({
    pagina: 0,
    tamanho: 5,
    nome: "",
    mensagem: "",
    alunoSelecionado: {} as Aluno,
    alunosFiltrados:[],

    setPagina: (novaPagina: number) => set(() => ({pagina: novaPagina})),
    setNome: (novoNome: string) => set(() => ({nome: novoNome})),
    setMensagem: (novaMensagem: string) => set(() => ({mensagem: novaMensagem})),
    setAlunoSelecionado: (novoAlunoSelecionado:Aluno) => set(() => ({alunoSelecionado: novoAlunoSelecionado})),
    setAlunosFiltrados: (novosAlunosFiltrados:Aluno[]) => set(() => ({alunosFiltrados: novosAlunosFiltrados})),

}))
export default useAlunoStore;