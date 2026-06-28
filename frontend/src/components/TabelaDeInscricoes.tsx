import { Link } from "react-router-dom";
import type { Inscricao } from "../interfaces/Inscricao";

interface Props {
  inscricoes: Inscricao[];
}

const TabelaDeInscricoes = ({ inscricoes: inscricoes }: Props) => {
  return (
    <div className="table-responsive">
      <table className="table table-bordered table-striped table-hover table-sm">
        <thead>
          <tr>
            <th className="text-center align-middle">Nome</th>
            <th className="text-center align-middle">E-mail</th>
          </tr>
        </thead>
        <tbody>
          {inscricoes.map((inscricao) => (
            <tr key={inscricao.id}>
              <td width="13%" className="text-center align-middle">
                <Link to={"/aluno/"+inscricao.aluno.id}>{inscricao.aluno.nome}</Link>
              </td>
              <td width="13%" className="text-center align-middle">
                {inscricao.aluno.email}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default TabelaDeInscricoes;
