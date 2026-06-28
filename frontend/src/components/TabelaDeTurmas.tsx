import { Link } from "react-router-dom";
import type { Turma } from "../interfaces/Turma";

interface Props {
  turmas: Turma[];
}

const TabelaDeTurmas = ({ turmas }: Props) => {
  return (
    <div className="table-responsive">
      <table className="table table-bordered table-striped table-hover table-sm">
        <thead>
          <tr>
            <th className="text-center align-middle">Id</th>
            <th className="text-center align-middle">Código</th>
            <th className="text-center align-middle">Disciplina</th>
            <th className="text-center align-middle">Ano</th>
            <th className="text-center align-middle">Período</th>
            <th className="text-center align-middle">Professor</th>
          </tr>
        </thead>
        <tbody>
          {turmas.map((turma) => (
            <tr key={turma.id}>
              <td width="8%" className="text-center align-middle">
                <Link to={"/turmas/" + turma.id}>{turma.id}</Link>
              </td>
              <td width="13%" className="text-center align-middle">
                {turma.codigo}
              </td>
              <td width="13%" className="text-center align-middle">
                {turma.disciplina.nome}
              </td>
              <td width="13%" className="text-center align-middle">
                {turma.ano}
              </td>
              <td width="13%" className="text-center align-middle">
                {turma.periodo}
              </td>
              <td width="13%" className="text-center align-middle">
                {turma.professor.nome}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default TabelaDeTurmas;
