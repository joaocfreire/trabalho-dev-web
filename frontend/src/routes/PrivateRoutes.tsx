import { Navigate, useLocation } from "react-router-dom";
import Layout from "./Layout";
import useTokenStore from "../store/TokenStore";

const PrivateRoutes = () => {
  const tokenResponse = useTokenStore((s) => s.tokenResponse);

  const location = useLocation();
  // const navigate = useNavigate();

  console.log(location.pathname);

  if (tokenResponse.idUsuario > 0) {
    return <Layout />
  }
  else {
    // navigate("/login");
    return <Navigate to="/login" state={{destino: location.pathname}} />
  }
};
export default PrivateRoutes;
