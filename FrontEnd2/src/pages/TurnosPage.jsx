import { useState, useEffect } from 'react'
import { fetchData } from '../services/api'

export default function TurnosPage() {
  const [turnos, setTurnos] = useState([])
  const [campanas, setCampanas] = useState([])
  const [tiendas, setTiendas] = useState([])
  const [voluntarioEntidad, setVoluntarioEntidad] = useState([])
  const [voluntarioFisico, setVoluntarioFisico] = useState([])
  const [usuarios, setUsuarios] = useState([])
  const [selectedCampana, setSelectedCampana] = useState('')
  const [selectedTienda, setSelectedTienda] = useState('')

  useEffect(() => {
    Promise.all([
      fetchData('turnos'),
      fetchData('campanas'),
      fetchData('tiendas'),
      fetchData('voluntario_base'),
      fetchData('voluntario_entidad'),
      fetchData('voluntario_fisico'),
      fetchData('usuarios')
    ]).then(([t, camp, ti, vb, ve, vf, u]) => {
      setTurnos(t)
      setCampanas(camp)
      setTiendas(ti)
      setVoluntarioEntidad(ve)
      setVoluntarioFisico(vf)
      setUsuarios(u)
    }).catch(err => console.error(err))
  }, [])

  function getVoluntarioDisplay(idVoluntario) {
    const fisico = voluntarioFisico.find(v => Number(v.id_voluntario) === Number(idVoluntario))
    if (fisico) return `${fisico.nombre} ${fisico.apellidos}`
    const entidad = voluntarioEntidad.find(v => Number(v.id_voluntario) === Number(idVoluntario))
    if (entidad) return `${entidad.nombre_asociacion} (${entidad.n_voluntarios})`
    return `Voluntario #${idVoluntario}`
  }

  function getUsuarioName(id) {
    const user = usuarios.find(u => Number(u.id) === Number(id))
    return user ? `${user.nombre} ${user.apellidos}` : 'No asignado'
  }


  const filteredTurnos = selectedCampana && selectedTienda
    ? turnos.filter(t =>
        Number(t.id_campana) === Number(selectedCampana) &&
        Number(t.id_tienda) === Number(selectedTienda)
      )
    : []

  const tiendaSel = selectedTienda ? tiendas.find(t => Number(t.id) === Number(selectedTienda)) : null
  const capitanNombre = tiendaSel && tiendaSel.id_capitan
    ? getUsuarioName(tiendaSel.id_capitan)
    : 'Sin asignar'

  function formatDate(dateStr) {
    const fecha = new Date(dateStr)
    return fecha.toLocaleDateString('es-ES', { weekday: 'long', day: '2-digit', month: '2-digit' })
  }

  return (
    <>
      <header className="header">
        <h1>Asignación de Turnos</h1>
      </header>
      <div className="card filtros-turnos">
        <div>
          <label htmlFor="select-campana">Campaña:</label>
          <select id="select-campana" value={selectedCampana} onChange={e => setSelectedCampana(e.target.value)}>
            <option value="">-- Seleccione Campaña --</option>
            {campanas.map(c => (
              <option key={c.id} value={c.id}>{c.nombre}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="select-tienda">Tienda:</label>
          <select id="select-tienda" value={selectedTienda} onChange={e => setSelectedTienda(e.target.value)}>
            <option value="">-- Seleccione Tienda --</option>
            {tiendas.map(t => (
              <option key={t.id} value={t.id}>{t.nombre}</option>
            ))}
          </select>
        </div>
      </div>

      
        <div id="cuadrante-container">
          <div className="card">
            <div className="cuadrante-header">
              <h3>Cuadrante de Turnos</h3>
              <div className="cuadrante-actions">
                <span>Capitán: <strong id="capitan-nombre">{capitanNombre}</strong></span>
                <button className="btn btn-success btn-add-extra">+ Añadir Turno Extra</button>
              </div>
            </div>
            <table className="cuadrante-tabla">
              <thead>
                <tr>
                  <th>Día</th>
                  <th>Inicio</th>
                  <th>Fin</th>
                  <th>Voluntarios Asignados</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody id="tabla-turnos-body">
                {filteredTurnos.length === 0 ? (
                  <tr><td colSpan={5} style={{ textAlign: 'center' }}>No hay turnos registrados para esta selección.</td></tr>
                ) : (
                  filteredTurnos.map((turno, i) => (
                    <tr key={i}>
                      <td style={{ textTransform: 'capitalize' }}>{formatDate(turno.dia)}</td>
                      <td>{turno.hora_inicio}</td>
                      <td>{turno.hora_fin}</td>
                      <td>
                        <div className="voluntarios-cell">
                          <span className="voluntario-tag">
                            {getVoluntarioDisplay(turno.id_voluntario)} <button className="btn-remove">×</button>
                          </span>
                          <button className="btn btn-sm btn-add">+ Añadir</button>
                        </div>
                      </td>
                      <td>
                        <button className="btn btn-danger btn-incidence">Incidencia</button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      
    </>
  )
}
