import { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../auth/useAuthHook'
import logoSrc from '../assets/logo.png'

export default function Sidebar() {
  const [open, setOpen] = useState(false)
  const navigate = useNavigate()
  const { logout } = useAuth()

  function toggle() {
    setOpen(prev => !prev)
  }

  function handleLogout() {
    logout()
    navigate('/')
  }

  const sidebarClass = `sidebar${open ? ' abierto' : ''}`
  const enlacesClass = `abierto`

  return (
    <>
      <button className="btn btn-primary btn-collapsible" onClick={toggle}>
        {open ? '✕' : '☰'}
      </button>
      <aside className={sidebarClass}>
        <img src={logoSrc} alt="Logo BANCOSOL" className="logo_login" />
        <nav id="menu-enlaces" className={enlacesClass}>
          <ul>
            <li>
              <NavLink to="/dashboard" className={({ isActive }) => isActive ? 'active' : ''}>
                Cuadro de Mando
              </NavLink>
            </li>
            <li>
              <NavLink to="/campanas" className={({ isActive }) => isActive ? 'active' : ''}>
                Gestión de Campañas
              </NavLink>
            </li>
            <li>
              <NavLink to="/cadenas" className={({ isActive }) => isActive ? 'active' : ''}>
                Gestión de Cadenas
              </NavLink>
            </li>
            <li>
              <NavLink to="/tiendas" className={({ isActive }) => isActive ? 'active' : ''}>
                Gestión de Tiendas
              </NavLink>
            </li>
            <li>
              <NavLink to="/colaboradores" className={({ isActive }) => isActive ? 'active' : ''}>
                Colaboradores
              </NavLink>
            </li>
            <li>
              <NavLink to="/usuarios" className={({ isActive }) => isActive ? 'active' : ''}>
                Usuarios
              </NavLink>
            </li>
            <li>
              <NavLink to="/turnos" className={({ isActive }) => isActive ? 'active' : ''}>
                Asignación de Turnos
              </NavLink>
            </li>
            <li>
              <NavLink to="/bandeja" className={({ isActive }) => isActive ? 'active' : ''}>
                Bandeja de Entrada
              </NavLink>
            </li>
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
