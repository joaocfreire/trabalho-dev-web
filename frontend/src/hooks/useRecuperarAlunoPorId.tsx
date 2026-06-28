import { useQuery } from "@tanstack/react-query";

import type { Aluno } from "../interfaces/Aluno";
import useApi from "./useApi"; 

import { URL_ALUNOS } from "../util/constants";
/**
 * Hook customizado para recuperar um Aluno por ID usando React Query (useQuery).
 * Ele utiliza o hook useApi para encapsular a lógica de autenticação (GET) 
 * e tratamento de erros.
 */
const useRecuperarAlunoPorId = (id: string) => {
  
  // 1. Instanciando o useApi para o tipo Aluno e o endpoint "/alunos".
  // Requer que a função 'recuperarPorId' esteja implementada no useApi.
  const { recuperarPorParametro } = useApi<Aluno>(URL_ALUNOS);

  // 2. Retorna a configuração do useQuery
  return useQuery({
    // A queryKey deve incluir o ID para diferenciar o cache de cada aluno
    queryKey: ["alunos", id], 
  
    queryFn: () => recuperarPorParametro(id), 
    
    staleTime: 10_000, // obsoleto após 10 segundos
    
    // Opcional: só executa a query se o ID for uma string válida (útil se o ID for 
    // opcional ou vier de um parâmetro de rota que pode ser undefined)
    enabled: !!id, 
  });
};

export default useRecuperarAlunoPorId;