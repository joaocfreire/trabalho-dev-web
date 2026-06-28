import type { ResultadoPaginado } from "../interfaces/ResultadoPaginado";
import { URL_BASE } from "../util/constants";
import useFetchWithAuth from "./useFetchWithAuth";

const useApi = <T>(endpoint: string) => {
  const { fetchWithAuth } = useFetchWithAuth();
  const URL = `${URL_BASE}${endpoint}`;

const incluir = async (obj: T) => {
    const response = await fetchWithAuth(URL, {
      method: "POST", // <--- Método POST para criação
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(obj),
    });
    
    if (!response.ok) {
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro ao incluir: " + " - Status code: " + response.status
        );
    }
    return await response.json();
};


  const alterar = async (obj: T) => {
    const response = await fetchWithAuth(URL, {
      method: "PUT",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(obj),
    });
    if (!response.ok) {
      // Se ocorrer um erro 401 ou 403 fetchWithAuth já terá capturado o erro e
      // encaminhado a requisição para a página de login.
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro desconhecido: " + " - Status code: " + response.status
        );
    }
    return await response.json();
  };

  const recuperarComPaginacao = async (
    queryString: Record<string, string>
  ): Promise<ResultadoPaginado<T>> => {
    const response = await fetchWithAuth(
      `${URL}/paginacao?` +
        new URLSearchParams({ ...queryString })
    );
    if (!response.ok) {
      // Se ocorrer um erro 401 ou 403 então a linha abaixo com "return await response.json()"
      // dará erro pois não retornará json.
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro desconhecido: " + " - Status code: " + response.status
        );
    }
    return await response.json();
  };

  const recuperarTodos = async (): Promise<T> => {
    // T representa o tipo de retorno esperado, que neste caso será T[] (Array de T) no uso.
    const response = await fetchWithAuth(URL); 
    if (!response.ok) {
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro ao recuperar todos: " + " - Status code: " + response.status
        );
    }
    return await response.json();
  };

const excluir = async (id: number | string) => {
    const response = await fetchWithAuth(`${URL}/${id}`, { 
      method: "DELETE", // Método DELETE para exclusão
    });
    // Não espera JSON, apenas verifica a resposta 
    if (!response.ok) {
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro ao excluir: " + " - Status code: " + response.status
        );
    }
    // Retorna void ou true/false, dependendo da necessidade
    return; 
  };

  const recuperarPorParametro = async (sufixo: string): Promise<T> => {
    // Sufixo será a parte da URL que vem após o endpoint base, 
    // por exemplo: "/turma=1" ou "/filtro?status=ativo"
    const response = await fetchWithAuth(`${URL}${"/"+sufixo}`); 
    if (!response.ok) {
      const error: any = await response.json().catch(() => ({}));
      if (error) throw error;
      else
        throw new Error(
          "Erro ao recuperar por parâmetro: " + " - Status code: " + response.status
        );
    }
    return await response.json();
  };

  return {incluir, alterar, recuperarComPaginacao,recuperarTodos,excluir,recuperarPorParametro };
};
export default useApi;
