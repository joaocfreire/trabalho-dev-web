import { useQuery } from "@tanstack/react-query";

import type { Aluno } from "../interfaces/Aluno"; 
import useApi from "./useApi"; 

// Endpoint base para Aluno
import { URL_ALUNOS } from "../util/constants";


/**
 * Hook customizado para recuperar a lista completa de Alunos usando React Query (useQuery).
 * Utiliza o useApi para encapsular a segurança (fetchWithAuth) e o tratamento de erros.
 */
const useRecuperarAlunos = () => {
  
  // 1. Instanciando o useApi: O retorno esperado é um array de Alunos (Aluno[]).
  // Reutiliza a função 'recuperarTodos' que faz um GET simples no endpoint.
  const { recuperarTodos } = useApi<Aluno[]>(URL_ALUNOS); 

  // 2. Retorna a configuração do useQuery
  return useQuery({
    queryKey: ["alunos"], 
    
    /**
     * queryFn agora chama diretamente a função 'recuperarTodos' do useApi.
     * O useApi.recuperarTodos fará o: GET http://localhost:8080/alunos
     * (com o cabeçalho de Authorization).
     */
    queryFn: recuperarTodos, 
    
    staleTime: 10_000, // obsoleto após 10 segundos
  });
};

export default useRecuperarAlunos;