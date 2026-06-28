import { useParams } from "react-router-dom";
import { useRecuperarTurmaEDetalhes } from "../hooks/useRecuperarTurmaEDetalhes";
import type { Inscricao } from "../interfaces/Inscricao";
import useRecuperarInscricoesPorTurma from "../hooks/useRecuperarInscricoesPorTurma";

export default function TurmaDetalhesPage() {
  const { id } = useParams();
  const { 
    data: inscricoes, 
    error: errorRecuperarIncricoes 
  } = useRecuperarInscricoesPorTurma(id!);
  const {
    data: turma,
    isPending: carregandoTurma,
    error: erroRecuperarTurma,
  } = useRecuperarTurmaEDetalhes(id!);

  if (erroRecuperarTurma || errorRecuperarIncricoes)
    return <p>Erro recuperando detalhes da turma.</p>;
  if (carregandoTurma || !turma) return <p>Carregando...</p>;

  return (
    <>
      <h1>Detalhes da Turma</h1>
      <div className="turma">
        <p>
          <strong>Id:</strong> {turma.id}
        </p>
        <p>
          <strong>Professor:</strong> {turma.professor?.nome}
        </p>
      </div>

      <div className="turma-inscricoes">
        <h2>Inscrições</h2>
        {inscricoes && inscricoes.length > 0 ? (
          <ul>
            {inscricoes.map((inscricao: Inscricao) => (
              <li key={inscricao.id}>
                {inscricao.aluno.nome} ({inscricao.aluno.email}) —{" "}
                {inscricao.dataHora}
              </li>
            ))}
          </ul>
        ) : (
          <p>Não há inscrições nesta turma.</p>
        )}
      </div>
    </>
  );
}
