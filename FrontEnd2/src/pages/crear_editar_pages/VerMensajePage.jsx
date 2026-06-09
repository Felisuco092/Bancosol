import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { fetchData } from '../../services/api'

export default function VerMensajePage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const [error, setError] = useState(null)
  const [notificacion, setNotificacion] = useState(null)

  useEffect(() => {
    fetchData('notificaciones/' + id)
      .then(setNotificacion)
      .catch(err => setError(err.message))
  }, [])

  if (error) {
    return (
      <>
        <header className="header">
          <h1>Ver Mensaje</h1>
        </header>
        <div className="formulario">
          <p className="error-message">{error}</p>
          <div className="form-actions">
            <button type="button" className="btn btn-secondary" onClick={() => navigate('/bandeja')}>Volver</button>
          </div>
        </div>
      </>
    )
  }

  if (!notificacion) {
    return (
      <>
        <header className="header">
          <h1>Ver Mensaje</h1>
        </header>
        <div className="formulario">
          <p className="text-center">Cargando...</p>
        </div>
      </>
    )
  }

  return (
    <>
      <header className="header">
        <h1>Mensaje | Asunto: {notificacion.asunto}</h1>
      </header>

      <div className="formulario">
        <div className="form-group">
          <label>Mensaje:</label>
          <p>{notificacion.mensaje}</p>
        </div>
        <div className="form-actions">
          <button type="button" className="btn btn-secondary" onClick={() => navigate('/bandeja')}>Volver</button>
        </div>
      </div>
    </>
  )
}
