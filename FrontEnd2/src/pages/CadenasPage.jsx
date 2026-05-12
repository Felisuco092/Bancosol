import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData } from '../services/api'

export default function CadenasPage() {
  const [cadenas, setCadenas] = useState([])
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchData('cadenas')
      .then(setCadenas)
      .catch(err => {
        console.error('Error al cargar datos:', err)
        setError('Error al cargar los datos. Por favor, intente de nuevo.')
      })
  }, [])

  return (
    <>
      <header className="header">
        <h1>Gestión de Cadenas</h1>
        <Link to="/tiendas/crear" className="btn btn-primary">+ Nueva Cadena</Link>
      </header>
      <div className="card">
        <table id="tabla-cadenas" className="tabla-cadenas">
          <thead>
            <tr>
              <th>Nombre de Cadena</th>
              <th>Código</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody id="chain-table-body">
            {error ? (
              <tr><td colSpan={3} className="text-center">{error}</td></tr>
            ) : cadenas.length === 0 ? (
              <tr><td colSpan={3} className="text-center">No se encontraron cadenas</td></tr>
            ) : (
              cadenas.map(cadena => (
                <tr key={cadena.id}>
                  <td><strong>{cadena.nombre}</strong></td>
                  <td>{cadena.codigo || 'N/A'}</td>
                  <td>
                    <button className="btn btn-primary btn-sm">Editar</button>
                    <button className="btn btn-danger btn-sm">Eliminar</button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </>
  )
}
