import { isRouteErrorResponse, useRouteError, Link } from "react-router-dom";
import NavBar from "../components/NavBar";

const ErrorPage = () => {
  const error = useRouteError();

  const errorMessage = isRouteErrorResponse(error)
    ? "A página solicitada não foi encontrada."
    : error instanceof Error
    ? error.message
    : "Ocorreu um erro inesperado.";

  return (
    <>
      <NavBar />

      <div
        className="d-flex justify-content-center align-items-center"
        style={{
          minHeight: "90vh",
          background: "#eaf6ff",
        }}
      >
        <div
          className="card shadow-lg border-0 text-center p-5"
          style={{
            maxWidth: "600px",
            borderRadius: "20px",
          }}
        >
          <div style={{ fontSize: "70px" }}>⚠️</div>

          <h1 className="text-primary mt-3">Oops!</h1>

          <h4 className="text-secondary mb-3">
            Algo deu errado
          </h4>

          <p className="text-muted">
            {errorMessage}
          </p>

          <div className="mt-4">
            <Link to="/" className="btn btn-primary px-4">
              Voltar para a página inicial
            </Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default ErrorPage;