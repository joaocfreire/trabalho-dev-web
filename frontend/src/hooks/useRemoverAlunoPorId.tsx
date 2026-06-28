import { useMutation, useQuery } from "@tanstack/react-query";

import type { Aluno } from "../interfaces/Aluno";
import useApi from "./useApi"; 

import { URL_ALUNOS } from "../util/constants";
import { queryClient } from "../main";
/**
 * Hook customizado para recuperar um Aluno por ID usando React Query (useQuery).
 * Ele utiliza o hook useApi para encapsular a lógica de autenticação (GET) 
 * e tratamento de erros.
 */
const useRemoverAlunoPorId = () => {
  const { excluir } = useApi<Aluno>(URL_ALUNOS);

 return useMutation({
    
    mutationFn: (id:string) => excluir(id),
      onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["alunos"],
        exact: false
      });
    },
    onError: (error)=>{
      throw error
    }
  });
};

export default useRemoverAlunoPorId;