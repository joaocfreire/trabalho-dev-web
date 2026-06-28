import AlunoForm from "../components/AlunoForm"
import CadastrarUsuarioForm from "../components/CadastrarUsuarioForm"

const CadastrarAlunoPage = () => {
  return (
    <>
      <div className="mb-4">
        <h5>Cadastro de Usuários</h5>
        <hr className="mt-1" />
      </div>

      <CadastrarUsuarioForm />
    </>
  )
}

export default CadastrarAlunoPage