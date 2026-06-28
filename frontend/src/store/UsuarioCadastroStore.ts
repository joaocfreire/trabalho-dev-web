import { create } from "zustand";
import type { UsuarioCadastro } from "../interfaces/UsuarioCadastro";

interface UsuarioCadastroStore {
    mensagem: string;
    usuarioCadastroSelecionado: UsuarioCadastro;

    setMensagem: (novaMensagem: string) => void;
    setUsuarioCadastroSelecionado: (novoUsuarioCadastro: UsuarioCadastro) => void;

}

const useUsuarioCadastroStore = create<UsuarioCadastroStore>((set) => ({
    mensagem: "",
    usuarioCadastroSelecionado: {} as UsuarioCadastro,

    setMensagem: (novaMensagem: string) => set(() => ({mensagem: novaMensagem})),
    setUsuarioCadastroSelecionado: (novoUsuarioCadastro:UsuarioCadastro) => set(() => ({usuarioCadastroSelecionado: novoUsuarioCadastro})),

}))
export default useUsuarioCadastroStore;