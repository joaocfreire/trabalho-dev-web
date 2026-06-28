import { useQuery } from "@tanstack/react-query";
import type { ResultadoPaginado } from "../interfaces/ResultadoPaginado";
import type { Inscricao } from "../interfaces/Inscricao";
import useApi from "./useApi"; 
import { URL_INSCRICOES } from "../util/constants";

// O tipo QueryString deve ser mantido, pois é o contrato de entrada do hook
interface QueryString {
  pagina: string;
  tamanho: string;
  turma?: string;
}

const useRecuperarInscricoesPorTurmaComPaginacao = (queryString: QueryString) => {
  
  // 1. Instanciando o useApi: O retorno esperado é ResultadoPaginado<Inscricao>.
  // Reutilizamos a função 'recuperarComPaginacao' do useApi.
  const { recuperarComPaginacao } = useApi<Inscricao>(URL_INSCRICOES); 

  // 2. Retorna a configuração do useQuery
  return useQuery({
    // A queryKey deve ser estável, incluindo todos os parâmetros de filtro/paginação
    queryKey: ["inscricoes", "paginacao", queryString], 
    
    /**
     * queryFn chama a função recuperarComPaginacao, que:
     * 1. Monta a URL: /inscricoes/paginacao?pagina=...&tamanho=...&turma=...
     * 2. Adiciona o cabeçalho Authorization (fetchWithAuth)
     * 3. Lida com os erros 401/403 e outros erros de status.
     */
    queryFn: () => recuperarComPaginacao(queryString as unknown as Record<string, string>), 
    
    staleTime: 10_000, // obsoleto após 10 segundos
    
    // Como a paginação é essencial para a UI, geralmente deixamos 'enabled' como padrão (true),
    // a menos que um parâmetro específico esteja faltando.
  });
};

export default useRecuperarInscricoesPorTurmaComPaginacao;