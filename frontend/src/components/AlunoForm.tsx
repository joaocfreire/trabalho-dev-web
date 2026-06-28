import z from "zod";
import useAlunoStore from "../store/AlunoStore";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useEffect } from "react";
import type { Aluno } from "../interfaces/Aluno";
import { zodResolver } from "@hookform/resolvers/zod";
import useCadastrarAluno from "../hooks/useCadastrarAluno";
import useAlterarAluno from "../hooks/useAlterarAluno";

const schema = z.object({ // cria um objeto do zod com restrições
  nome: z
    .string()
    .nonempty({ message: "O 'nome' deve ser informado." })
    .min(3, { message: "O 'nome' deve ter pelo menos 3 caracteres." }),
  email: z
    .string()
    .nonempty("O e-mail deve ser informado."),
});

type FormAluno = z.infer<typeof schema>;

const AlunoForm = () => {
  const setMensagem = useAlunoStore((s) => s.setMensagem);
  const alunoSelecionado = useAlunoStore((s) => s.alunoSelecionado); // usado para preencher em caso de edição

  const navigate = useNavigate();
  const { mutate: cadastrarAluno, error: errorCadastrarProduto } = useCadastrarAluno();
  const { mutate: alterarAluno, error: errorAlterarProduto } = useAlterarAluno();

  const { register, handleSubmit, reset, setValue, formState: {errors} } = useForm<FormAluno>({resolver: zodResolver(schema)});

  const inicializaForm = () => {
    if (alunoSelecionado.id) {
      setValue("nome", alunoSelecionado.nome);
      setValue("email", alunoSelecionado.email);
    } else {
      reset();
    }
  };

  useEffect(() => { // roda a cada vez que alunoSelecionado mudar
    inicializaForm();
  }, [alunoSelecionado]);

  const submit = ({
    nome,
    email,
  }: FormAluno) => {
    const aluno: Aluno = {
      nome: nome,
      email: email,
    };
    if (alunoSelecionado.id) {
      aluno.id = alunoSelecionado.id;  
      alterarAluno(aluno, {
        onSuccess: (alunoAlterado: Aluno) => {
          setMensagem("Aluno atualizado.");
          navigate("/aluno/" + alunoAlterado.id);
        },
      });
    } else {
      cadastrarAluno(aluno, {
        onSuccess: (alunoCadastrado: Aluno) => {
          setMensagem("Aluno cadastrado.");
          navigate("/aluno/"+alunoCadastrado.id);
        },
      });
    }
  };

  if (errorCadastrarProduto) throw errorCadastrarProduto;
  if (errorAlterarProduto) throw errorAlterarProduto;
  
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
      </div>
      <div className="row mb-5">
        <div className="col-xl-6">
          <div className="row">
            <div className="col-xl-10 d-flex">
              <button
                type="submit"
                className="btn btn-azul btn-sm d-flex align-items-left me-2"
              >
                {alunoSelecionado.id ? (
                  <>
                    Alterar
                  </>
                ) : (
                  <>
                    Cadastrar
                  </>
                )}
              </button>
              <button
                onClick={() => inicializaForm()}
                type="button"
                className="btn btn-vermelho btn-sm d-flex align-items-left"
              >
                Cancelar
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
};
export default AlunoForm;


