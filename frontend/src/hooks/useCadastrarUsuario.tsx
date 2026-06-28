import { useMutation } from "@tanstack/react-query";
import { queryClient } from "../main";
// Não é mais necessário: import isErrorResponse from "../util/isErrorResponse"; 

import useApi from "./useApi"; 
import{URL_USUARIOS } from "../util/constants";
import type { UsuarioCadastro } from "../interfaces/UsuarioCadastro";

/**
 * Hook customizado para cadastrar um novo Aluno usando React Query (useMutation).
 * Ele utiliza o hook useApi para encapsular a lógica de autenticação (POST) 
 * e tratamento de erros.
 */
const useCadastrarUsuario = () => {
  
  const { incluir } = useApi<UsuarioCadastro>(URL_USUARIOS);

  // Retorna a configuração do useMutation
  return useMutation({
    /**
     * mutationFn agora chama diretamente a função 'incluir' do useApi.
     * Esta função deve realizar o fetch com o método POST.
     */
    mutationFn: (usuario: UsuarioCadastro) => incluir(usuario),
    
    // Ações de sucesso: Invalida o cache após o cadastro bem-sucedido.
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["usuarios"],
        exact: false
      });
    }
  });
};

export default useCadastrarUsuario;