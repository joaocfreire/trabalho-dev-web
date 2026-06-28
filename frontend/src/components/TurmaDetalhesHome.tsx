import { useParams } from "react-router-dom";
import { useRecuperarTurmaEDetalhes } from "../hooks/useRecuperarTurmaEDetalhes";
import InscricoesComPaginacaoPage from "../pages/InscricoesComPaginacaoPage";

const TurmaDetalhesHome = () => {
  const { idTurma } = useParams();

  const {
    data: turma,
    isPending: carregandoTurma,
    error: erroRecuperarTurma,
  } = useRecuperarTurmaEDetalhes(idTurma!);

  if (carregandoTurma) {
    return <p>Recuperando detalhes da turma...</p>;
  }
  if (erroRecuperarTurma) {
    return <p>Erro ao recuperar detalhes da turma.</p>;
  }


  if (idTurma && turma) {
    return (
      <>
        <h1>{"Detalhes da Turma " + idTurma}</h1>
        <div className="turma">
          <p>
            <strong>Professor:</strong> {turma.professor?.nome }<br/>
            <strong>Ano:</strong> {turma.ano}<br/>
            <strong>Período:</strong> {turma.periodo}<br/>
          </p>
        </div>
        <hr />
        <h1>Lista de Alunos</h1>
        <hr />
        <InscricoesComPaginacaoPage idTurma={idTurma} />
      </>
    );
  } else {
    return <h1>Clique em uma turma para ver seus detalhes.</h1>;
  }
};
export default TurmaDetalhesHome;
