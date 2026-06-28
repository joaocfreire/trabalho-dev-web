import TabelaDeAlunos from "../components/TabelaDeAlunos";
import useRecuperarAlunos from "../hooks/useRecuperarAlunos";

const AlunosPage = () => {
  const {
    data: alunos,
    isPending: recuperandoAlunos,
    error: errorRecuperarAlunos,
  } = useRecuperarAlunos();

  if (errorRecuperarAlunos) throw errorRecuperarAlunos;
  if (recuperandoAlunos) return <p>Recuperando alunos...</p>;

  return (
    <>
      <h5>Lista de Alunos</h5>
      <hr className="mt-1" />

      <TabelaDeAlunos alunos={alunos} />
    </>
  );
};
export default AlunosPage;
