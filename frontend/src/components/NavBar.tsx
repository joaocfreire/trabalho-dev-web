import { NavLink } from "react-router-dom";
import logo from "../assets/logo.jpg";
import "bootstrap-icons/font/bootstrap-icons.css";
import useTokenStore from "../store/TokenStore";

const NavBar = () => {
  const tokenResponse = useTokenStore((s) => s.tokenResponse);

  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      
      <div className="container">
        <NavLink className="navbar-brand" to="/">
          <img src={logo} width="50px" alt="logo" />
        </NavLink>
        {tokenResponse.nome?<p className="me-5 mt-3">Olá, {tokenResponse.nome}</p>:<></>}
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <NavLink className="nav-link" aria-current="page" to="/">
                Home
              </NavLink>
            </li>
          </ul>
          <ul className="navbar-nav ms-auto">
            <li className="nav-item">
              <NavLink className="nav-link" to="/listar-alunos">
                <i className="bi bi-card-list me-1"></i>
                Listar Alunos
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/listar-turmas">
                <i className="bi bi-card-list me-1"></i>
                Listar Turmas
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/listar-alunos-por-turma">
                <i className="bi bi-card-list me-1"></i>
                Listar Alunos Por Turma
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/cadastrar-aluno">
                <i className="bi bi-card-list me-1"></i>
                Cadastrar Aluno
              </NavLink>
            </li>
            {(tokenResponse.idUsuario > 0 && tokenResponse.role == "ADMIN") ? (
             <li className="nav-item">
              <NavLink className="nav-link" to="/cadastrar-usuario">
                    <i className="bi bi-box-arrow-in-left me-1"></i>
                    Cadastrar Usuário
              </NavLink>
             </li>
                ) : (
                  <>
                   
                  </>
                )}
              
            <li className="nav-item">
              <NavLink className="nav-link" to="/login">
                {tokenResponse.idUsuario > 0 ? (
                  <>
                    <i className="bi bi-box-arrow-in-left me-1"></i>
                    Sair
                  </>
                ) : (
                  <>
                    <i className="bi bi-box-arrow-in-right me-1"></i>
                    Entrar
                  </>
                )}
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};
export default NavBar;
