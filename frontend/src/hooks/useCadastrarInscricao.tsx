import { useMutation } from "@tanstack/react-query";
import { queryClient } from "../main";
// Não é mais necessário: import isErrorResponse from "../util/isErrorResponse"; 

import type { Inscricao } from "../interfaces/Inscricao";
// Não é mais necessário: import type { Aluno } from "../interfaces/Aluno"; 
import useApi from "./useApi"; 

import { URL_ALUNOS, URL_INSCRICOES } from "../util/constants";

/**
 * Hook customizado para cadastrar uma nova Inscricao usando React Query (useMutation).
 * Ele utiliza o hook useApi para encapsular a lógica de autenticação (POST) 
 * e tratamento de erros.
 */
const useCadastrarInscricao = () => {
  
  // 1. Instanciando o useApi para o tipo Inscricao e o endpoint "/inscricoes".
  // Reutilizamos a função 'incluir' (que usa POST) do useApi.
  const { incluir } = useApi<Inscricao>(URL_INSCRICOES);

  // 2. Retorna a configuração do useMutation
  return useMutation({
    /**
     * mutationFn agora chama diretamente a função 'incluir' do useApi.
     */
    mutationFn: (inscricao: Inscricao) => incluir(inscricao),
    
    // 3. Ações de sucesso: Invalida o cache após o cadastro bem-sucedido.
    onSuccess: () => {
      // Invalida as queries que usam a chave ["inscricoes"]
      queryClient.invalidateQueries({
        queryKey: ["inscricoes"],
        exact: false
      });
    }
  });
};

export default useCadastrarInscricao;