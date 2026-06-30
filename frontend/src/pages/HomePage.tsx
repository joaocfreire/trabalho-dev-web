import { NavLink, Outlet } from "react-router-dom";
import useRecuperarTurmas from "../hooks/useRecuperarTurmas";
import type { Turma } from "../interfaces/Turma";
import { useState } from "react";
import { Formatters } from "../util/Formatters";

const HomePage = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const { data: turmas, error: errorRecuperarTurmas } = useRecuperarTurmas();
  

  if (errorRecuperarTurmas) {
    throw errorRecuperarTurmas;
  }


  // 1. Alteração: Filtrar as turmas APENAS com base no nome da disciplina
  const filteredTurmas = turmas
    ? turmas.filter((turma: Turma) => {
        const term = searchTerm.toLowerCase();
        if(term == ""){
          return false;
        }
        // Obter o nome da disciplina e converter para minúsculas
        const disciplinaNome = turma.disciplina.nome.toLowerCase()+" "+turma.codigo.toLowerCase();
        // Obter o termo de pesquisa e converter para minúsculas
        
        // Verificar se o nome da disciplina inclui o termo de pesquisa
        return disciplinaNome.includes(term);
      })
    : [];

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  return (
    <>
      {turmas && turmas.length > 0 ? (
        <div className="row">
          <div className="col-lg-3">
            <h5>Turmas</h5>
            {}
            <div className="mb-3">
              <input
                type="text"
                className="form-control"
                placeholder="Pesquisar por disciplina..." // Altere o placeholder para ser mais claro
                value={searchTerm}
                onChange={handleSearchChange}
              />
            </div>
            
            <nav className="nav nav-pills d-flex flex-column">
              {}
              {filteredTurmas.map((turma: Turma) => (
                <NavLink className="nav-link" key={turma.id} to={"/" + turma.id}>
                  {Formatters.getFullTurmaNome(turma)}
                </NavLink>
              ))}
              {}
              {filteredTurmas.length === 0 && searchTerm !== "" && (
                <div className="mt-2 text-muted">Nenhuma turma encontrada com o nome digitado.</div>
              )}
            </nav>
          </div>
          <div className="col-lg-9">
            <Outlet />
          </div>
        </div>
      ) : (
        <div></div>
      )}
    </>
  );
};

export default HomePage;