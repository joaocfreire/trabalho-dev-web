import { Link } from "react-router-dom";
import LoginForm from "../components/LoginForm";

const LoginPage = () => {
  return (
    <>
      <div className="mb-4">
        <h5>Página de Login</h5>
        <hr className="mt-1" />
      </div>

      <LoginForm />
      <Link to="/cadastrar-usuario">Não tenho conta</Link>
    </>
  );
};
export default LoginPage;
