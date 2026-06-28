import { useMutation } from "@tanstack/react-query";
import type { UsuarioLogin } from "../interfaces/UsuarioLogin";
import { URL_AUTENTICACAO, URL_BASE } from "../util/constants";
import isErrorResponse from "../util/isErrorResponse";

const useEfetuarLogin = () => {
  const efetuarLogin = async (usuarioLogin: UsuarioLogin) => {
    // ***************************************************************
    // *   Único lugar onde chamamos fetch em vez de fetchWithAuth   *
    // ***************************************************************
    const response = await fetch(`${URL_BASE}${URL_AUTENTICACAO}/login`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(usuarioLogin),
    });
    if (!response.ok) {
      // O único erro esperado aqui é o 401
      // Se o email ou a senha não forem preenchidos ou o email não for válido
      // o erro retornado será do tipo JSON, isto é, do tipo ErrorResponse. E 
      // caso a validação não esteja sendo realizada no cliente com Zod então
      // o error será do tipo Errorresponse.

      // Na linha abaixo estamos verificando se o erro que ocorreu é do tipo JSON
      // Será do tipo JSON caso um ErrorResponse seja retornado do back-end.
      // Mas o servidor só retornará um ErrorResponse se não estiver ocorrendo 
      // validação no cliente com Zod.  
      const error: any = await response.json().catch(() => ({}));
      console.log("dentro de useEfetuarLogin - error = ", error);
      if (error) {
        if (isErrorResponse(error)) {
          // Só irá entrar aqui caso estejamos fazendo validação de entrada de dados
          // apenas no servidor com o spring Validator. Se estivermos utilizando o Zod
          // não entrará aqui. 
          throw error;
        }
        else {
          throw new Error(
            "Erro desconhecido ao efetuar login. Status code = " + response.status
          );
        }
      } else {
        // Sempre que ocorre um erro de login (401 ou 403) o erro retornado não contém 
        // um json. E response.text() também não retorna nada. 

        // Ao efetuar login não é possível ocorrer o erro 403 pois todos os usuários têm 
        // o direito de tentar efetuar login.
        if (response.status === 401) {
          console.log("O status code do erro que ocorreu é " + response.status);
          // ************************************************************************
          // *   O erro que está sendo lançado abaixo será capturado em FormLogin   *
          // ************************************************************************
          throw new Error(
            "Erro ao efetuar login. Status code = " + response.status
          );
        } else {
          throw new Error(
            "Erro desconhecido ao efetuar login. Status code = " + response.status
          );
        }
      }
    }

    return await response.json();
  };

  return useMutation({
    mutationFn: (usuarioLogin: UsuarioLogin) => efetuarLogin(usuarioLogin),
  });
};
export default useEfetuarLogin;
