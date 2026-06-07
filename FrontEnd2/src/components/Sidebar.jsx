import { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/useAuthHook'
import { Roles } from '../utils/constants'
import logoSrc from '../assets/logo.png'

export default function Sidebar() {
  const [open, setOpen] = useState(false)
  const navigate = useNavigate()
  const { logout, usuario: user } = useAuth()

  function toggle() {
    setOpen(prev => !prev)
  }

  function handleLogout() {
    logout()
    navigate('/')
  }

  const sidebarClass = `sidebar${open ? ' abierto' : ''}`
  const enlacesClass = `abierto`

  const menuItems = [
    { to: '/dashboard', label: 'Cuadro de Mando', roles: [Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD, Roles.RESP_TIENDA] },
    { to: '/campanas', label: 'Gestión de Campañas', roles: [Roles.ADMIN] },
    { to: '/cadenas', label: 'Gestión de Cadenas', roles: [Roles.ADMIN] },
    { to: '/tiendas', label: 'Gestión de Tiendas', roles: [Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_TIENDA] },
    { to: '/colaboradores', label: 'Colaboradores', roles: [Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD] },
    { to: '/usuarios', label: 'Usuarios', roles: [Roles.ADMIN] },
    { to: '/turnos', label: 'Asignación de Turnos', roles: [Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD, Roles.RESP_TIENDA] },
    { to: '/bandeja', label: 'Bandeja de Entrada', roles: [Roles.ADMIN, Roles.CAPITAN, Roles.COORDINADOR, Roles.RESP_ENTIDAD, Roles.RESP_TIENDA] },
  ]

  return (
    <>
      <button className="btn btn-primary btn-collapsible" onClick={toggle}>
        {open ? '✕' : '☰'}
      </button>
      <aside className={sidebarClass}>
        <img src={logoSrc} alt="Logo BANCOSOL" className="logo_login" />
        <nav id="menu-enlaces" className={enlacesClass}>
          <ul>
            {menuItems
              .filter(item => item.roles.includes(user?.id_rol))
              .map(item => (
                <li key={item.to}>
                  <NavLink to={item.to} className={({ isActive }) => (isActive ? 'active' : '')}>
                    {item.label}
                  </NavLink>
                </li>
              ))}
            <li>
              <a href="#" className="text-logout" onClick={handleLogout}>
                Cerrar Sesión
              </a>
            </li>
          </ul>
        </nav>
      </aside>
    </>
  )
}
