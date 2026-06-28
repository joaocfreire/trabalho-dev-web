
import { useNavigate, useParams } from "react-router-dom";
import useRecuperarAlunoPorId from "../hooks/useRecuperarAlunoPorId";
import useAlunoStore from "../store/AlunoStore";
import type { Aluno } from "../interfaces/Aluno";
import { useEffect } from "react";


const AlunoPage = () => {
  const {idAluno} = useParams();
  const setAlunoSelecionado = useAlunoStore((s) => s.setAlunoSelecionado); // usado para preencher em caso de edição
  const navigate = useNavigate();
  useEffect(()=>{
    setAlunoSelecionado({}as Aluno)
  }, []);
  const tratarEdicao = (aluno: Aluno) => {
    setAlunoSelecionado(aluno);
    navigate("/cadastrar-aluno");
  };
  const {
    data: aluno,
    isPending: recuperandoAluno,
    error: errorRecuperarAluno,
  } = useRecuperarAlunoPorId(idAluno!);
  if(errorRecuperarAluno){
    throw errorRecuperarAluno;
  }
  if(recuperandoAluno){
    return(<h1>Recuperando aluno...</h1>);
  }
  return (
    <>
        <h1>
            {"Id: "+aluno.id}
        </h1>
        <h1>
            {"Aluno: "+aluno.nome}
        </h1>
        <h1>
            {"E-mail: "+aluno.email}
        </h1>
        <div className="col-lg-3 col-md-4 col-6 mt-3">
          <button onClick={() => tratarEdicao(aluno)} 
                  className="btn btn-azul btn-sm w-100" type="button">
            Editar
          </button>
        </div>
    </>
  )
}
export default AlunoPage;