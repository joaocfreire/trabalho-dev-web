import { useQuery } from "@tanstack/react-query";
import type { Turma } from "../interfaces/Turma";
import useApi from "./useApi"; // ✅ Importando o useApi
import {URL_TURMAS} from "../util/constants";


/**
 * Hook customizado para recuperar uma Turma por ID e seus detalhes, 
 * utilizando o useApi para encapsular a segurança e o tratamento de erros.
 */
export const useRecuperarTurmaEDetalhes = (id: string | undefined) => {
    
  // 1. Instanciando o useApi para o tipo Turma.
  const { recuperarPorId } = useApi<Turma>(URL_TURMAS); 

  // 2. Define o valor inicial (placeholder data) para quando o ID não estiver disponível.
  const initialTurma: Turma = { /* Inclua aqui todos os campos da Turma com valores padrão */ };
  
  return useQuery<Turma>({
    queryKey: ["turma", id],
    
    /**
     * queryFn chama a função recuperarPorId. 
     * O useApi.recuperarPorId fará o: GET http://localhost:8080/turmas/{id}
     */
    queryFn: () => recuperarPorId(id!), // Usamos '!' pois o 'enabled' garante que o fetch só ocorrerá com ID.
    
    staleTime: 10000,
    
  
    // O fetch só será executado se o 'id' for verdadeiro (não undefined ou vazio).
    enabled: !!id, 
    
  
    // Garante que o hook retorne os dados vazios esperados quando 'enabled' é false.
    placeholderData: initialTurma,
    
    // A tipagem do select é necessária se o 'placeholderData' for usado:
    select: (data) => data, 
  });
};