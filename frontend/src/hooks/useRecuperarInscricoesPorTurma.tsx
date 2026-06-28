import { useQuery } from "@tanstack/react-query";

import type { Inscricao } from "../interfaces/Inscricao";
import useApi from "./useApi"; 
import { URL_INSCRICOES } from "../util/constants";


const useRecuperarInscricoesPorTurma = (turmaId: string) => {
  
  // 1. Instanciando o useApi para o tipo esperado (Array de Inscrições).
  const { recuperarPorParametro } = useApi<Inscricao[]>(URL_INSCRICOES); 

  // 2. Define a função que monta o sufixo e chama o useApi.
  const queryFn = () => {
      // Monta a parte da URL que será anexada ao ENDPOINT_INSCRICOES (/inscricoes)
      const sufixo = "turma=" + turmaId;
      return recuperarPorParametro(sufixo);
  }

  // 3. Retorna a configuração do useQuery
  return useQuery({
    // A queryKey deve incluir o turmaId para cache específico
    queryKey: ["inscricoes", turmaId], 
    
    queryFn: queryFn,
    
    staleTime: 10_000, // obsoleto após 10 segundos
    
    // O fetch só será executado se o turmaId for uma string não vazia.
    // Isso substitui o "if (turmaId) { ... }" que existia no código original.
    enabled: !!turmaId, 
  });
};

export default useRecuperarInscricoesPorTurma;