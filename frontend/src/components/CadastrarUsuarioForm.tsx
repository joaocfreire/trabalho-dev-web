import z from "zod";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import type {UsuarioCadastro } from "../interfaces/UsuarioCadastro";
import { zodResolver } from "@hookform/resolvers/zod";
import useCadastrarUsuario from "../hooks/useCadastrarUsuario";
import useTokenStore from "../store/TokenStore";

const schema = z.object({ // cria um objeto do zod com restrições
  nome: z
    .string()
    .nonempty({ message: "O 'nome' deve ser informado." })
    .min(3, { message: "O 'nome' deve ter pelo menos 3 caracteres." }),
  email: z
    .string()
    .nonempty("O e-mail deve ser informado."),
  senha: z.string().nonempty("Preencha a senha."),
  role: z.string().nonempty("Selecione um Role.").default("USER")
});

type FormUsuario = z.infer<typeof schema>;

const CadastrarUsuarioForm = () => {
  const tokenResponse = useTokenStore((s) => s.tokenResponse);

  const navigate = useNavigate();
  const { mutate: cadastrarUsuario, error: errorCadastrarUsuario } = useCadastrarUsuario();

  const { register, handleSubmit, formState: {errors} } = useForm<FormUsuario>({resolver: zodResolver(schema)});

  const submit = ({
    nome,
    email,
    role,
    senha

  }: FormUsuario) => {
    const usuario: UsuarioCadastro = {
      nome: nome,
      email: email,
      role: role,
      senha: senha,
    };
    
    cadastrarUsuario(usuario, {
      onSuccess: () => {
        navigate("/login");
      },
    });
    
  };

  if (errorCadastrarUsuario) throw errorCadastrarUsuario;
  
  return (
    <form onSubmit={handleSubmit(submit)} autoComplete="off">
      <div className="row">
        <div className="col-xl-3">
          <div className="row mb-2">
            <label htmlFor="nome" className="col-xl-3 fw-bold">
              Nome
            </label>
            <div className="col-xl-10">
              <input
                {...register("nome")}
                type="text"
                id="nome"
                className="form-control form-control-sm"
              />
              {errors.nome && <p style={{color: "red", 
                                         fontSize: "14px", 
                                         marginTop: "2px", 
                                         marginBottom: "0px"}}>{errors.nome.message}</p>}
            </div>
          </div>
        </div>

        <div className="col-xl-3">
          <div className="row mb-2">
            <label htmlFor="descricao" className="col-xl-3 fw-bold">
              E-mail
            </label>
            <div className="col-xl-10">
              <input
                {...register("email")}
                type="text"
                id="descricao"
                className="form-control form-control-sm"
              />
              {errors.email && <p style={{color: "red", 
                                              fontSize: "14px", 
                                              marginTop: "2px", 
                                              marginBottom: "0px"}}>{errors.email.message}</p>}
            </div>
          </div>
        </div>

         <div className="col-xl-3">
          <div className="row mb-2">
            <label htmlFor="senha" className="col-xl-3 fw-bold">
              Senha
            </label>
            <div className="col-xl-10">
              <input
                {...register("senha")}
                type="text"
                id="senha"
                className="form-control form-control-sm"
              />
              {errors.email && <p style={{color: "red", 
                                              fontSize: "14px", 
                                              marginTop: "2px", 
                                              marginBottom: "0px"}}>{errors.email.message}</p>}
            </div>
          </div>
        </div>
        
        {tokenResponse.role=="ADMIN"?(<div className="col-xl-3">
          <div className="row mb-2">
            <label htmlFor="role" className="col-xl-3 fw-bold">
              Role:
            </label>
            <div className="col-xl-10">
              <select{...register("role")} id="descricao" className="form-control form-control-sm">
                <option value="ADMIN">Administrador</option>
                <option value="USER">Usuário</option>
              </select>
              {errors.email && <p style={{color: "red", 
                                              fontSize: "14px", 
                                              marginTop: "2px", 
                                              marginBottom: "0px"}}>{errors.email.message}</p>}
            </div>
          </div>
        </div>):(<div></div>)}
        

      </div>
      <div className="row mb-5">
        <div className="col-xl-6">
          <div className="row">
            <div className="col-xl-10 d-flex">
              <button
                type="submit"
                className="btn btn-azul btn-sm d-flex align-items-left me-2"
              >Cadastrar</button>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};
export default CadastrarUsuarioForm;


