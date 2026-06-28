
import { Link } from "react-router-dom";
import type { Aluno } from "../interfaces/Aluno";
import useRemoverAlunoPorId from "../hooks/useRemoverAlunoPorId";

interface Props {
  alunos: Aluno[];
}

const TabelaDeAlunos = ({alunos}: Props) => {
  const {mutate:removerAluno, error: erroRemoverAluno} = useRemoverAlunoPorId();
  if(erroRemoverAluno){
    throw erroRemoverAluno;
  }
  return (
    <div className="table-responsive">
      <table className="table table-bordered table-striped table-hover table-sm">
        <thead>
          <tr>
            <th className="text-center align-middle">Id</th>
            <th className="text-center align-middle">Nome</th>
            <th className="text-center align-middle">E-mail</th>
            <th className="text-center align-middle">Remover</th>
          </tr>
        </thead>
        <tbody>
          {alunos.map((aluno) => (
            <tr key={aluno.id}>
              <td width="8%" className="text-center align-middle">
                {aluno.id}
              </td>
              <td width="13%" className="text-center align-middle">
                <Link to={"/aluno/"+aluno.id}>{aluno.nome}</Link>
              </td>
              <td width="13%" className="text-center align-middle">
                {aluno.email}
              </td>
              <td width="13%" className="text-center align-middle">
                  <button
                    onClick={() => removerAluno(aluno.id!.toString())}
                    className="btn-vermelho btn-sm"
                  >
                    Remover
                  </button>
            </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default TabelaDeAlunos;
