
import type { ErrorResponse } from "react-router-dom";

function isErrorResponse(error: any): error is ErrorResponse {
  return error && typeof error.requestUri === "string";
}
export default isErrorResponse;
