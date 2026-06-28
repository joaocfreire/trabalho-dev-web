import { useState } from "react";
import type { Inscricao } from "../interfaces/Inscricao";
import useRecuperarInscricoesPorTurmaComPaginacao from "../hooks/useRecuperarInscricoesPorTurmaComPaginacao";
import Paginacao from "../components/Paginacao";
import TabelaDeInscricoes from "../components/TabelaDeInscricoes";

interface Props {
  idTurma: string;
}

const InscricoesComPaginacaoPage = ({ idTurma }: Props) => {
  const [pagina, setPagina] = useState(0);
  const tamanho: number = 10;

  const {
    data: resultadoPaginado,
    isPending: carregandoInscricoes,
    error: erroRecuperarInscricoes,
  } = useRecuperarInscricoesPorTurmaComPaginacao({
    pagina: pagina.toString(),
    tamanho: tamanho.toString(),
    turma: idTurma?.toString(),
  });
  if (erroRecuperarInscricoes) {
    throw erroRecuperarInscricoes;
  }
  if (carregandoInscricoes) {
    return <p>Recuperando inscrições desta turma.</p>;
  }
  const inscricoes: Inscricao[] = resultadoPaginado
    ? resultadoPaginado.itens
    : [];
  const totalDePaginas: number = resultadoPaginado
    ? resultadoPaginado.totalDePaginas
    : 0;
  const tratarPaginacao = (pagina: number) => {
    setPagina(pagina);
  };
  return (
    <>
      <TabelaDeInscricoes inscricoes={inscricoes} />
      <Paginacao
        pagina={pagina}
        totalDePaginas={totalDePaginas}
        tratarPaginacao={tratarPaginacao}
      />
    </>
  );
};
export default InscricoesComPaginacaoPage;
