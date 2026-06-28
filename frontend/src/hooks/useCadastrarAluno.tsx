import { useMutation } from "@tanstack/react-query";
import { queryClient } from "../main";
// Não é mais necessário: import isErrorResponse from "../util/isErrorResponse"; 

import type { Aluno } from "../interfaces/Aluno";
import useApi from "./useApi"; 
import { URL_ALUNOS } from "../util/constants";

/**
 * Hook customizado para cadastrar um novo Aluno usando React Query (useMutation).
 * Ele utiliza o hook useApi para encapsular a lógica de autenticação (POST) 
 * e tratamento de erros.
 */
const useCadastrarAluno = () => {
  
  const { incluir } = useApi<Aluno>(URL_ALUNOS);

  // Retorna a configuração do useMutation
  return useMutation({
    /**
     * mutationFn agora chama diretamente a função 'incluir' do useApi.
     * Esta função deve realizar o fetch com o método POST.
     */
    mutationFn: (aluno: Aluno) => incluir(aluno),
    
    // Ações de sucesso: Invalida o cache após o cadastro bem-sucedido.
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["alunos"],
        exact: false
      });
    }
  });
};

export default useCadastrarAluno;