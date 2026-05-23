import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'

export default function TiendasPage() {
  const [tiendas, setTiendas] = useState([])
  const [cadenas, setCadenas] = useState([])
  const [campanas, setCampanas] = useState([])
  const [participa, setParticipa] = useState([])
  const [selectedCampana, setSelectedCampana] = useState('')
  const [selectedCadena, setSelectedCadena] = useState('todas')
  const [selectedLocalidad, setSelectedLocalidad] = useState('todas')
  useEffect(() => {
    Promise.all([
      fetchData('tiendas'),
      fetchData('cadenas'),
      fetchData('campanas'),
      fetchData('participa')
    ]).then(([t, c, camp, p]) => {
      setTiendas(t)
      setCadenas(c)
      setCampanas(camp)
      setParticipa(p)
    }).catch(err => console.error(err))
  }, [])

  async function handleDelete(tiendaId) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta tienda? Se eliminaran los turnos y participaciones asociadas.')) return
    try {
      const [participas, turnos] = await Promise.all([
        fetchData('participa?id_tienda=' + tiendaId),
        fetchData('turnos?id_tienda=' + tiendaId)
      ])
      await Promise.all([
        ...participas.map(p => deleteData('participa/' + p.id)),
        ...turnos.map(t => deleteData('turnos/' + t.id))
      ])
      await deleteData('tiendas/' + tiendaId)
      setTiendas(prev => prev.filter(t => String(t.id) !== String(tiendaId)))
      alert('Tienda eliminada con éxito')
    } catch (err) {
      console.error('Error al eliminar tienda:', err)
      alert('No se pudo eliminar la tienda')
    }
  }

  function getParticipaState(campanaId, tiendaId) {
    return participa.some(p =>
      String(p.id_campana) === String(campanaId) &&
      String(p.id_tienda) === String(tiendaId)
    )
  }

  const localidades = [...new Set(tiendas.map(t => t.localidad))]

  const filteredTiendas = !selectedCampana ? [] : tiendas.filter(tienda => {
    const matchCadena = selectedCadena === 'todas' || Number(tienda.id_cadena) === Number(selectedCadena)
    const matchLocalidad = selectedLocalidad === 'todas' || tienda.localidad.toLowerCase() === selectedLocalidad
    return matchCadena && matchLocalidad
  })

  return (
    <>
      <header className="header">
        <h1>Gestión de Tiendas</h1>
        <Link to="/tiendas/crear" className="btn btn-primary">+ Nueva Tienda</Link>
      </header>
      <div className="card">
        <div className="filtros-grid">
          <div>
            <label htmlFor="select-filtro-campanas">Campaña:</label>
            <select id="select-filtro-campanas" value={selectedCampana} onChange={e => setSelectedCampana(e.target.value)}>
              <option value="">Seleccionar Campaña...</option>
              {campanas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="filtro-por-cadenas">Cadena:</label>
            <select id="filtro-por-cadenas" value={selectedCadena} onChange={e => setSelectedCadena(e.target.value)}>
              <option value="todas">Seleccionar Cadena...</option>
              {cadenas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="filtro-por-localidad">Localidad:</label>
            <select id="filtro-por-localidad" value={selectedLocalidad} onChange={e => setSelectedLocalidad(e.target.value)}>
              <option value="todas">Seleccionar Localidad...</option>
              {localidades.map(loc => (
                <option key={loc} value={loc.toLowerCase()}>{loc}</option>
              ))}
            </select>
          </div>
        </div>

        {!selectedCampana ? (
          <div className="card" style={{ textAlign: 'center', padding: '2rem', color: '#666' }}>
            Seleccione una campaña para visualizar las tiendas
          </div>
        ) : (
          <table id="tabla-tiendas" className="tabla-tiendas">
            <thead>
              <tr>
                <th>Tienda</th>
                <th>Localidad</th>
                <th>Domicilio</th>
                <th>C.P.</th>
                <th>Zona</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {filteredTiendas.map(tienda => {
                const participates = getParticipaState(selectedCampana, tienda.id)
                const statusClass = participates ? 'status-activa' : 'status-inactiva'
                const statusText = participates ? 'Participa' : 'No participa'
                return (
                  <tr key={tienda.id} className="clickable">
                    <td>{tienda.nombre}</td>
                    <td>{tienda.localidad.toUpperCase()}</td>
                    <td>{tienda.domicilio}</td>
                    <td>{tienda.c_postal}</td>
                    <td>{tienda.zona_geografica}</td>
                    <td><span className={`status-badge ${statusClass}`}>{statusText}</span></td>
                    <td>
                      <Link to={`/tiendas/editar/${tienda.id}`} className="btn btn-primary btn-sm">Editar</Link>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(tienda.id)}>Borrar</button>
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        )}
      </div>
    </>
  )
}
