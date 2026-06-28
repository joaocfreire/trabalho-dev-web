import TabelaDeTurmas from "../components/TabelaDeTurmas";
import useRecuperarTurmas from "../hooks/useRecuperarTurmas";

const TurmasPage = () => {
  const {
    data: turmas,
    isPending: recuperandoTurmas,
    error: errorRecuperarTurmas,
  } = useRecuperarTurmas();

  if (errorRecuperarTurmas) throw errorRecuperarTurmas;
  if (recuperandoTurmas) return <p>Recuperando turmas...</p>;

  return (
    <>
      <h5>Lista de Turmas</h5>
      <hr className="mt-1" />

      <TabelaDeTurmas turmas={turmas} />
    </>
  );
};
export default TurmasPage;
