import { useState } from 'react';
import { ContextoAuten } from './ContextoAuten';

const PERMISOS_POR_ROL = {
  1: { EDITAR_TIENDA: true, EDITAR_TURNOS: true, INCIDENCIAS: true,
       EDITAR_COLABORADORES: true, BORRAR_COLABORADORES: true },
  2: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: true,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
  3: { EDITAR_TIENDA: false, EDITAR_TURNOS: true, INCIDENCIAS: true,
       EDITAR_COLABORADORES: true, BORRAR_COLABORADORES: false },
  4: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: false,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
  5: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: false,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
}

export function ProveedorAuten({ children }) {
  const [usuario, setUsuario] = useState(() => {
    const guardado = sessionStorage.getItem('user');
    return guardado ? JSON.parse(guardado) : null;
  });

  function tienePermiso(permiso) {
    if (!usuario) return false
    const permisosRol = PERMISOS_POR_ROL[usuario.id_rol]
    return permisosRol ? permisosRol[permiso] === true : false
  }

  const login = (userData, token) => {
    console.log('Login called with:', { userData, token });
    sessionStorage.setItem('user', JSON.stringify(userData));
    sessionStorage.setItem('token', token);
    setUsuario(userData);
  };

  const logout = () => {
    console.log('Logout called');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    setUsuario(null);
  };

  return (
    <ContextoAuten value={{ usuario, estaAutenticado: !!usuario, login, logout, tienePermiso }}>
      {children}
    </ContextoAuten>
  );
}