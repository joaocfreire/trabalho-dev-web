import { useQuery } from "@tanstack/react-query";
// Importações necessárias
import type { Turma } from "../interfaces/Turma"; 
import useApi from "./useApi"; 
import {URL_TURMAS} from "../util/constants";
// Endpoint base


const useRecuperarTurmas = () => {
  
  // 1. Instanciando o useApi: O retorno esperado é um array de Turmas (Turma[]).
  // Reutiliza a função 'recuperarTodos' que faz um GET simples no endpoint.
  const { recuperarTodos } = useApi<Turma[]>(URL_TURMAS); 

  // 2. Retorna a configuração do useQuery
  return useQuery({
    queryKey: ["turmas"], 
    
    /**
     * queryFn chama diretamente a função 'recuperarTodos' do useApi.
     * O useApi garante que o token de autenticação seja enviado.
     */
    queryFn: recuperarTodos, 
    
    staleTime: 10_000, // obsoleto após 10 segundos
  });
};

export default useRecuperarTurmas;