import type { Aluno } from "../interfaces/Aluno";
import type { Inscricao } from "../interfaces/Inscricao";

interface Props {
  inscricoes: Inscricao[];
  grupo: number[];
  adicionarAoGrupo: (aluno: Aluno) => void;
  removerDoGrupo: (aluno: Aluno) => void;
}
const isInGrupo = (id: number, grupo: number[]) => { // não consegui fazer funcionar com o find
  for (let i = 0; i < grupo.length; i++) {
    if (id == grupo[i]) {
      return true;
    }
  }
  return false;
};
const TabelaDeInscricoesComAddGrupo = ({
  inscricoes: inscricoes,
  grupo: grupo,
  adicionarAoGrupo,
  removerDoGrupo,
}: Props) => {
  console.log(grupo);
  return (
    <div className="table-responsive">
      <table className="table table-bordered table-striped table-hover table-sm">
        <thead>
          <tr>
            <th className="text-center align-middle">Nome</th>
            <th className="text-center align-middle">E-mail</th>
            <th className="text-center align-middle">Grupo</th>
          </tr>
        </thead>
        <tbody>
          {inscricoes.map((inscricao) => (
            <tr key={inscricao.id}>
              <td width="13%" className="text-center align-middle">
                {inscricao.aluno.nome}
              </td>
              <td width="13%" className="text-center align-middle">
                {inscricao.aluno.email}
              </td>
              <td width="13%" className="text-center align-middle">
                {isInGrupo(inscricao.aluno.id!, grupo) ? (
                  <button
                    onClick={() => removerDoGrupo(inscricao.aluno)}
                    className="btn-vermelho btn-sm"
                  >
                    Remover
                  </button>
                ) : (
                  <button
                    onClick={() => adicionarAoGrupo(inscricao.aluno)}
                    className="btn-azul btn-sm"
                  >
                    Adicionar
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default TabelaDeInscricoesComAddGrupo;
