import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'

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

  async function handleDelete(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta cadena? También se eliminarán las tiendas asociadas.')) return
    try {
      const tiendas = await fetchData('tiendas?id_cadena=' + id)
      for (const tienda of tiendas) {
        const [participas, turnos] = await Promise.all([
          fetchData('participa?id_tienda=' + tienda.id),
          fetchData('turnos?id_tienda=' + tienda.id)
        ])
        await Promise.all([
          ...participas.map(p => deleteData('participa/' + p.id)),
          ...turnos.map(t => deleteData('turnos/' + t.id)),
          deleteData('tiendas/' + tienda.id)
        ])
      }
      await deleteData('cadenas/' + id)
      setCadenas(prev => prev.filter(c => String(c.id) !== String(id)))
      alert('Cadena eliminada con éxito')
    } catch (err) {
      console.error('Error al eliminar cadena:', err)
      alert('No se pudo eliminar la cadena')
    }
  }

  return (
    <>
      <header className="header">
        <h1>Gestión de Cadenas</h1>
        <Link to="/cadenas/crear" className="btn btn-primary">+ Nueva Cadena</Link>
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
                    <Link to={`/cadenas/editar/${cadena.id}`} className="btn btn-primary btn-sm">Editar</Link>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(cadena.id)}>Eliminar</button>
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
