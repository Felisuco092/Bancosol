import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'

export default function BandejaPage() {
  const [notificaciones, setNotificaciones] = useState([])

  useEffect(() => {
    fetchData('notificaciones').then(setNotificaciones).catch(console.error)
  }, [])

  function dateToString(date) {
    return new Date(date).toLocaleDateString('es-ES')
  }

  function handleDelete(id) {
    deleteData('notificaciones/' + id)
      .then(() => {
        setNotificaciones(prev => prev.filter(n => n.id !== id))
      })
      .catch(console.error)
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
                  <Link to={`/bandeja/ver/${n.id}`} className="btn btn-primary btn-sm" style={{marginRight: '0.5rem'}}>Ver mensaje</Link>
                  <button onClick={() => handleDelete(n.id)} className="btn btn-danger btn-sm">Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
