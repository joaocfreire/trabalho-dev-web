import { useMutation } from "@tanstack/react-query";
import { queryClient } from "../main";
import type { Aluno } from "../interfaces/Aluno";
import useApi from "./useApi"; 
import { URL_ALUNOS } from "../util/constants";


/**
 * Hook customizado para alterar um Aluno usando React Query (useMutation).
 */
const useAlterarAluno = () => {
  
  // 1. Instanciando o useApi para o tipo Aluno e o endpoint "/alunos".
  const { alterar } = useApi<Aluno>(URL_ALUNOS);

  // 2. Retorna a configuração do useMutation
  return useMutation({
    mutationFn: (aluno: Aluno) => alterar(aluno),
    
    // 3. Ações de sucesso: Invalida o cache após a alteração bem-sucedida.
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["alunos"],
        exact: false
      });
    }
  });
};

export default useAlterarAluno;