import { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useLocation, useNavigate } from "react-router-dom";
import loginIcon from "../assets/skin/login.png";
import useEfetuarLogin from "../hooks/useEfetuarLogin";
import type { TokenResponse } from "../interfaces/TokenResponse";
import type { UsuarioLogin } from "../interfaces/UsuarioLogin";
import useTokenStore from "../store/TokenStore";
import useLoginStore from "../store/LoginStore";
import isErrorResponse from "../util/isErrorResponse";
import z from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

const schema = z.object({
  email: z
    .email("Informe um email válido."),
  senha: z
    .string()
    .nonempty("Informe a senha.")
});

type FormLogin = z.infer<typeof schema>;


const LoginForm = () => {
  const setTokenResponse = useTokenStore((s) => s.setTokenResponse);
  const loginInvalido = useLoginStore((s) => s.loginInvalido);
  const msg = useLoginStore((s) => s.msg);

  const setLoginInvalido = useLoginStore((s) => s.setLoginInvalido);
  const setMsg = useLoginStore((s) => s.setMsg);

  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    // Efetua logout sempre que entramos na página de login
    setTokenResponse({ idUsuario: 0, token: "", nome: "", role: "" }); // Logout
  
    // Função de limpeza: executada ao sair da página de login.
    return () => {
      setLoginInvalido(false);
      setMsg("");
    };
  }, []);

  const { register, handleSubmit, formState: {errors} } = useForm<FormLogin>({resolver: zodResolver(schema)});
  
  const { mutate: efetuarLogin } = useEfetuarLogin();
  // console.log(register("email"));

  const submit = ({ email, senha }: FormLogin) => {
    const usuarioLogin: UsuarioLogin = { email, senha };
    efetuarLogin(usuarioLogin, {
      onSuccess: (tokenResp: TokenResponse) => {
        // Salva no zustand para indicar que o usuário está logado e para salvar o token
        setTokenResponse({
          idUsuario: tokenResp.idUsuario,
          token: tokenResp.token,
          nome: tokenResp.nome,
          role: tokenResp.role,
        });
        if (location.state?.destino) {
          navigate(location.state.destino);
        } else {
          navigate("/");
        }
      },
      onError: (error) => {
        if (isErrorResponse(error)) {
          // Esse conteúdo só será executado se não houver validação no cliente.
          // Isto é, se houver validação apenas no back-end. Pois havendo validação 
          // no cliente com o Zod, se houver um erro a requisição não será submetida
          // para o back-end. Consequentemente não entrará aqui em onError.
          // Por esta razão, não será exibida a mensagem "Login inválido" se ocorrer 
          // um erro de validação detectado pelo Zod.
          setLoginInvalido(true);
          setMsg("Login inválido");
        } else {
          console.log("deu erro", error);
          // Aqui nunca irá ocorrer o erro 403 pois todos os usuários podem 
          // tentar efetuar login. Um erro 403 só ocorrerá quando um usuário
          // estive logado e tentar fazer algo sem possuir o respectivo Role.
          // *****************************************************************
          // *   Aqui estamos capturando o erro lançado em useEfetuarLogin   *
          // *****************************************************************
          
          // Ocorrerá o erro 401 quando o usuário tentar acessar um recurso que
          // requer login.

          if (error.message.includes("401")) {
            setLoginInvalido(true);
            setMsg("Email ou senha inválidos.");
          } else {
            setLoginInvalido(true);
            setMsg(
              "Não foi possível efetuar o login. Por favor, tente mais tarde."
            );
          }
        }
      },
    });
  };

  // if (errorEfetuarLogin) throw errorEfetuarLogin;

  return (
    // Ao submeter uma requisição, se o zod detectar um erro esse erro será armazenado 
    // em errors de formState e handleSubmit não irá submeter a requisição chamando a 
    // função submit. Ao ser atualizada a variável errors de formState a página é 
    // renderizada novamente e as mensagens de erro são exibidas.
    <form autoComplete="off" onSubmit={handleSubmit(submit)}>
      <div className="row">
        <div className="col-lg-6">
          {loginInvalido && (
            <div className="alert alert-danger fw-bold" role="alert">
              {msg}
            </div>
          )}
        </div>
      </div>
      <div className="row mb-2">
        <label htmlFor="email" className="col-lg-1 fw-bold mb-2">
          Email
        </label>
        <div className="col-lg-5">
          <input
            {...register("email")}
            type="text"
            id="email"
            className="form-control form-control-sm"
          />
          {errors.email && <p style={{color: "red", 
                                      fontSize: "14px", 
                                      marginTop: "2px", 
                                      marginBottom: "0px"}}>{errors.email.message}</p>}
        </div>
      </div>

      <div className="row mb-3">
        <label htmlFor="senha" className="col-lg-1 fw-bold mb-2">
          Senha
        </label>
        <div className="col-lg-5">
          <input
            {...register("senha")}
            type="password"
            id="senha"
            className="form-control form-control-sm"
          />
          {errors.senha && <p style={{color: "red", 
                                      fontSize: "14px", 
                                      marginTop: "2px", 
                                      marginBottom: "0px"}}>{errors.senha.message}</p>}
        </div>
      </div>

      <div className="row">
        <div className="offset-lg-1 col-lg-5">
          <button type="submit" className="btn btn-outline-success">
            <img src={loginIcon} /> Entrar
          </button>
        </div>
      </div>
    </form>
  );
};
export default LoginForm;
