import { create } from "zustand";
import { type Turma } from "../interfaces/Turma";

interface TurmaStore {
    pagina: number;
    tamanho: number;
    nome: string;
    mensagem: string;
    turmaSelecionado: Turma|null;
    idTurma: number;
    turmasFiltradas:Turma[];
    grupo:number;

    setPagina: (novaPagina: number) => void;
    setNome: (novoNome: string) => void;
    setMensagem: (novaMensagem: string) => void;
    setTurmaSelecionado: (novoTurmaSelecionado: Turma) => void;
    setIdTurma: (novoId: number) => void;
    setTurmasFiltradas:(turmasFiltradas:Turma[])=>void;
    setGrupo:(grupo:number)=>void;
}

const useTurmaStore = create<TurmaStore>((set) => ({
    pagina: 0,
    tamanho: 5,
    nome: "",
    mensagem: "",
    turmaSelecionado: null,
    idTurma: 1,
    turmasFiltradas: [],
    grupo:-1,

    setPagina: (novaPagina: number) => set(() => ({pagina: novaPagina})),
    setNome: (novoNome: string) => set(() => ({nome: novoNome})),
    setMensagem: (novaMensagem: string) => set(() => ({mensagem: novaMensagem})),

    setTurmaSelecionado: (novoTurmaSelecionado:Turma) => set(() => ({
        turmaSelecionado: novoTurmaSelecionado
    })),

    setIdTurma: (novoId: number) => set(()=>({idTurma:novoId})),
    
    setTurmasFiltradas:(novoTurmasFiltradas: Turma[])=>set(()=>({
        turmasFiltradas:novoTurmasFiltradas.copyWithin(0,0)
    })),
    setGrupo: (novoGrupo: number) => set(() => ({grupo: novoGrupo})),

}));
export default useTurmaStore;