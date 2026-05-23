import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'

function getStatusInfo(start, end) {
  const now = new Date()
  if (now < new Date(start)) {
    return { status: 'proximamente', label: 'Próximamente', style: { background: '#fff3cd', color: '#856404' } }
  } else if (now > new Date(end)) {
    return { status: 'terminada', label: 'Terminada', style: { background: '#f8d7da', color: '#721c24' } }
  }
  return { status: 'activa', label: 'Activa', style: { background: '#d4edda', color: '#155724' } }
}

export default function CampanasPage() {
  const [campanas, setCampanas] = useState([])
  const [filter, setFilter] = useState('all')

  useEffect(() => {
    fetchData('campanas').then(setCampanas).catch(console.error)
  }, [])

  async function handleDelete(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta campaña?')) return
    try {
      await deleteData('campanas/' + id)
      setCampanas(prev => prev.filter(c => String(c.id) !== String(id)))
      alert('Campaña eliminada con éxito')
    } catch (err) {
      console.error('Error al eliminar campaña:', err)
      alert('No se pudo eliminar la campaña')
    }
  }

  const filtered = filter === 'all'
    ? campanas
    : campanas.filter(c => getStatusInfo(c.dia_comienzo, c.dia_final).status === filter)

  return (
    <>
      <header className="header">
        <h1>Gestión de Campañas</h1>
        <Link to="/campanas/crear" className="btn btn-primary">+ Nueva Campaña</Link>
      </header>
      <div className="card filtros-campanas">
        {['all', 'activa', 'terminada', 'proximamente'].map(f => (
          <button
            key={f}
            className={`btn filter-btn${filter === f ? ' active' : ''}`}
            onClick={() => setFilter(f)}
          >
            {f === 'all' ? 'Todas' : f.charAt(0).toUpperCase() + f.slice(1)}
          </button>
        ))}
      </div>
      <div className="card">
        <table>
          <thead>
            <tr>
              <th><b>Nombre de Campaña</b></th>
              <th><b>Año</b></th>
              <th><b>Inicio</b></th>
              <th><b>Fin</b></th>
              <th><b>Estado</b></th>
              <th><b>Acciones</b></th>
            </tr>
          </thead>
          <tbody id="campaign-table-body">
            {filtered.map((campana, i) => {
              const info = getStatusInfo(campana.dia_comienzo, campana.dia_final)
              return (
                <tr key={i} data-status={info.status}>
                  <td>{campana.nombre}</td>
                  <td>{campana.ano}</td>
                  <td>{campana.dia_comienzo}</td>
                  <td>{campana.dia_final}</td>
                  <td><span style={{ padding: '4px 8px', borderRadius: '4px', fontSize: '0.85rem', ...info.style }}>{info.label}</span></td>
                  <td>
                    <Link to={`/campanas/editar/${campana.id}`} className="btn btn-primary btn-sm">Editar</Link>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(campana.id)}>Eliminar</button>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>
    </>
  )
}
