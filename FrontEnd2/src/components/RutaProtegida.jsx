import { Navigate, Outlet } from 'react-router';
import { useAuth } from '../auth/useAuthHook';

export function ProtectedRoute({ allowedRoles }) {
  const { estaAutenticado, usuario } = useAuth();

  if (!estaAutenticado) {
    return <Navigate to="/" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(usuario?.id_rol)) {
    return <Navigate to="/dashboard" replace />;
  }

  return <Outlet />;
}