import { useState } from 'react';
import { ContextoAuten } from './ContextoAuten';
import { Roles } from '../utils/constants';

const PERMISOS_POR_ROL = {
  [Roles.ADMIN]: { EDITAR_TIENDA: true, EDITAR_TURNOS: true, INCIDENCIAS: true,
       EDITAR_COLABORADORES: true, BORRAR_COLABORADORES: true, CONFIRMAR_COLABORADORES: true },
  [Roles.CAPITAN]: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: true,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
  [Roles.COORDINADOR]: { EDITAR_TIENDA: false, EDITAR_TURNOS: true, INCIDENCIAS: true,
       EDITAR_COLABORADORES: true, BORRAR_COLABORADORES: false },
  [Roles.RESP_ENTIDAD]: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: false,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
  [Roles.RESP_TIENDA]: { EDITAR_TIENDA: false, EDITAR_TURNOS: false, INCIDENCIAS: false,
       EDITAR_COLABORADORES: false, BORRAR_COLABORADORES: false },
}

export function ProveedorAuten({ children }) {
  const [usuario, setUsuario] = useState(() => {
    const guardado = sessionStorage.getItem('user');
    if (!guardado) return null;
    const userData = JSON.parse(guardado);
    return { ...userData, id_rol: String(userData.id_rol) };
  });

  function tienePermiso(permiso) {
    if (!usuario) return false
    const permisosRol = PERMISOS_POR_ROL[usuario.id_rol]
    return permisosRol ? permisosRol[permiso] === true : false
  }

  const login = (userData, token) => {
    console.log('Login called with:', { userData, token });
    const normalizedUser = { ...userData, id_rol: String(userData.id_rol) };
    sessionStorage.setItem('user', JSON.stringify(normalizedUser));
    sessionStorage.setItem('token', token);
    setUsuario(normalizedUser);
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