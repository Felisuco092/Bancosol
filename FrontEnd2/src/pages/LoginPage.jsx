import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import logoSrc from '../assets/logo.png'

export default function LoginPage() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const navigate = useNavigate()

  function handleSubmit(e) {
    e.preventDefault()
    sessionStorage.setItem('user', username || 'admin')
    navigate('/dashboard')
  }

  return (
    <div className="login-card">
      <img src={logoSrc} alt="Logo BANCOSOL" className="logo_login" />
      <p className="login-subtitle">Acceso al sistema de gestión</p>
      <form id="login-form" onSubmit={handleSubmit}>
        <div className="input_login">
          <label htmlFor="username">Usuario:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={username}
            onChange={e => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input_login">
          <label htmlFor="password">Contraseña:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit" id="login-submit-btn" className="btn btn-primary w-full">
          Entrar
        </button>
      </form>
    </div>
  )
}
