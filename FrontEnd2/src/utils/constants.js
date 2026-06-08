const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:3001';

const Roles = {
  ADMIN: "1",
  CAPITAN: "2",
  COORDINADOR: "3",
  RESP_ENTIDAD: "4",
  RESP_TIENDA: "5"
};

export {API_BASE_URL, Roles} 