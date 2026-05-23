import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData } from '../services/api'

export default function BandejaPage() {
  const [notificaciones, setNotificaciones] = useState([])

  useEffect(() => {
    fetchData('notificaciones').then(setNotificaciones).catch(console.error)
  }, [])

  function dateToString(date) {
    return new Date(date).toLocaleDateString('es-ES')
  }

  return (
    <>
      <header className="header">
        <h1>Bandeja de Entrada</h1>
      </header>
      <div className="card">
        <h3>Notificaciones</h3>
        <table>
          <thead>
            <tr>
              <th>Fecha</th>
              <th>Asunto</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {notificaciones.map((n, i) => (
              <tr key={i}>
                <td>{dateToString(n.fecha_creacion)}</td>
                <td>{n.asunto}</td>
                <td>
                  <Link to={`/bandeja/ver/${n.id}`} className="btn btn-primary btn-view">Ver mensaje</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
