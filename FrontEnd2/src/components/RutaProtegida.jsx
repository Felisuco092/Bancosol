import { Navigate, Outlet } from 'react-router';
import { useAuth } from '../auth/useAuthHook';

export function ProtectedRoute() {
  const { estaAutenticado } = useAuth();

  if (!estaAutenticado) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}