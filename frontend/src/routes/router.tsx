import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "../pages/ErrorPage";
import HomePage from "../pages/HomePage";
import Layout from "./Layout";
import AlunosPage from "../pages/AlunosPage";
import TurmasPage from "../pages/TurmasPage";
import TurmaDetalhesPage from "../pages/TurmaDetalhesPage";
import TurmaDetalhesHome from "../components/TurmaDetalhesHome";
import AlunosPorTurmaPage from "../pages/AlunosPorTurmaPage";
import AlunoPage from "../pages/AlunoPage";
import CadastrarAlunoPage from "../pages/CadastrarAlunoPage";
import LoginPage from "../pages/LoginPage";
import PrivateRoutes from "./PrivateRoutes";
import CadastrarUsuarioPage from "../pages/CadastrarUsuarioPage";
const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "",
        element: <HomePage />,
        children: [
          {
            path: ":idTurma?",
            element: <TurmaDetalhesHome />,
          },
        ],
      },
      { path: "listar-alunos", element: <AlunosPage /> },
      { path: "listar-alunos-por-turma", element: <AlunosPorTurmaPage /> },
      { path: "listar-turmas", element: <TurmasPage /> },
      { path: "turmas/:id", element: <TurmaDetalhesPage /> },
      { path: "aluno/:idAluno", element: <AlunoPage /> },
      { path: "cadastrar-aluno", element: <CadastrarAlunoPage /> },
      { path: "cadastrar-usuario", element: <CadastrarUsuarioPage /> },
      {path: "login", element: <LoginPage />}
    ],
  },
  {
        path: "/",
        element: <PrivateRoutes />,
        errorElement: <ErrorPage />,
    }
]);
export default router;
