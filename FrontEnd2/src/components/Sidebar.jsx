import { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/useAuthHook'
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
    { to: '/dashboard', label: 'Cuadro de Mando', roles: [1, 2, 3, 4, 5] },
    { to: '/campanas', label: 'Gestión de Campañas', roles: [1] },
    { to: '/cadenas', label: 'Gestión de Cadenas', roles: [1] },
    { to: '/tiendas', label: 'Gestión de Tiendas', roles: [1, 2, 3, 5] },
    { to: '/colaboradores', label: 'Colaboradores', roles: [1, 2, 3, 4] },
    { to: '/usuarios', label: 'Usuarios', roles: [1] },
    { to: '/turnos', label: 'Asignación de Turnos', roles: [1, 2, 3, 4, 5] },
    { to: '/bandeja', label: 'Bandeja de Entrada', roles: [1, 2, 3, 4, 5] },
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
