import { useQuery } from "@tanstack/react-query";

import type { Inscricao } from "../interfaces/Inscricao"; 
import useApi from "./useApi"; 


import { URL_INSCRICOES } from "../util/constants";
/**
 * Hook customizado para recuperar a lista completa de Inscrições usando React Query (useQuery).
 * (A função queryFn foi renomeada para seguir a convenção RESTful do useApi).
 */
const useRecuperarInscricoes = () => {
  
  // 1. Instanciando o useApi: O retorno esperado é um array de Inscricao (Inscricao[]).
  // Reutiliza a função 'recuperarTodos' que faz um GET simples no endpoint.
  const { recuperarTodos } = useApi<Inscricao[]>(URL_INSCRICOES); 

  // 2. Retorna a configuração do useQuery
  return useQuery({
    queryKey: ["inscricoes"], 
    
    /**
     * queryFn agora chama diretamente a função 'recuperarTodos' do useApi.
     * O useApi.recuperarTodos fará o: GET http://localhost:8080/inscricoes
     * (com o cabeçalho de Authorization e tratamento de erro centralizado).
     */
    queryFn: recuperarTodos, 
    
    staleTime: 10_000, // obsoleto após 10 segundos
  });
};

export default useRecuperarInscricoes;