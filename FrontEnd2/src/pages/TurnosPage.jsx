import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'
import { useAuth } from '../auth/useAuthHook'
import { Roles } from '../utils/constants'

export default function TurnosPage() {
  const { tienePermiso, usuario } = useAuth()
  const [turnos, setTurnos] = useState([])
  const [campanas, setCampanas] = useState([])
  const [tiendas, setTiendas] = useState([])
  const [voluntarioEntidad, setVoluntarioEntidad] = useState([])
  const [voluntarioFisico, setVoluntarioFisico] = useState([])
  const [usuarios, setUsuarios] = useState([])
  // Estados para filtros
  const [selectedCampana, setSelectedCampana] = useState('')
  const [selectedTienda, setSelectedTienda] = useState('')

  //Tiendas del filtro dependen de la campaña seleccionada
  const [tiendasFiltradas, setTiendasFiltradas] = useState([])

  useEffect(() => {
    Promise.all([
      fetchData('turnos'),
      fetchData('campanas'),
      fetchData('tiendas'),
      fetchData('voluntario_entidad'),
      fetchData('voluntario_fisico'),
      fetchData('usuarios')
    ]).then(([t, camp, ti, ve, vf, u]) => {
      setTurnos(t)
      setCampanas(camp)
      setTiendas(ti)
      setVoluntarioEntidad(ve)
      setVoluntarioFisico(vf)
      setUsuarios(u)
    }).catch(err => console.error(err))
  }, [])

  function getVoluntarioDisplay(idVoluntario) {
    const fisico = voluntarioFisico.find(v => String(v.id_voluntario) === String(idVoluntario))
    if (fisico) return `${fisico.nombre} ${fisico.apellidos}`
    const entidad = voluntarioEntidad.find(v => String(v.id_voluntario) === String(idVoluntario))
    if (entidad) return `${entidad.nombre_asociacion} (${entidad.n_voluntarios})`
    return 'Sin asignar'
  }

  function getCampanaNombre(idCampana) {
    const camp = campanas.find(c => String(c.id) === String(idCampana))
    return camp ? `${camp.nombre} - ${camp.ano}` : ''
  }

  function getUsuarioName(id) {
    const user = usuarios.find(u => String(u.id) === String(id))
    return user ? `${user.nombre} ${user.apellidos}` : 'No asignado'
  }

  function handleDelete(id) {
    if (confirm('¿Estás seguro de que quieres eliminar este turno?')) {
      deleteData('turnos/' + id)
        .then(() => {
          setTurnos(prev => prev.filter(t => t.id !== id))
        })
        .catch(console.error)
    }
  }

  const filteredTurnos = selectedCampana && selectedTienda
    ? turnos.filter(t =>
        String(t.id_campana) === String(selectedCampana) &&
        String(t.id_tienda) === String(selectedTienda)
      )
    : []

  const tiendaSel = selectedTienda ? tiendas.find(t => String(t.id) === String(selectedTienda)) : null
  const capitanNombre = tiendaSel && tiendaSel.id_capitan
    ? getUsuarioName(tiendaSel.id_capitan)
    : 'Sin asignar'

  function formatDate(dateStr) {
    const fecha = new Date(dateStr)
    return fecha.toLocaleDateString('es-ES', { weekday: 'long', day: '2-digit', month: '2-digit' })
  }

  async function selectCampana(event) {
    const campanaId = event.target.value
    setSelectedCampana(campanaId)
    let participacionesCampana = []
    if (usuario.id_rol === Roles.COORDINADOR) {
      participacionesCampana = await fetchData(`participa?id_campana=${campanaId}&id_coordinador=${usuario.id}`)
    } else {
      participacionesCampana = await fetchData(`participa?id_campana=${campanaId}`)
    }
    const tiendasFiltradas = tiendas.filter(t => participacionesCampana.some(p => String(p.id_tienda) === String(t.id)))
    setTiendasFiltradas(tiendasFiltradas)
  }

  return (
    <>
      <header className="header">
        <h1>Asignación de Turnos</h1>
      </header>
      <div className="card filtros-turnos">
        <div>
          <label htmlFor="select-campana">Campaña:</label>
          <select id="select-campana" value={selectedCampana} onChange={selectCampana}>
            <option value="">-- Seleccione Campaña --</option>
            {campanas.map(c => (
              <option key={c.id} value={c.id}>{c.nombre} - {c.ano}</option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="select-tienda">Tienda:</label>
          <select id="select-tienda" value={selectedTienda} onChange={e => setSelectedTienda(e.target.value)} disabled={!selectedCampana}>
            <option value="">-- Seleccione Tienda --</option>
            {tiendasFiltradas.map(t => (
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
                {tienePermiso('EDITAR_TURNOS') && <Link to="/turnos/crear" className="btn btn-success btn-add-extra">+ Añadir Turno Extra</Link>}
              </div>
            </div>
            <table className="cuadrante-tabla">
              <thead>
                <tr>
                  <th>Día</th>
                  <th>Inicio</th>
                  <th>Fin</th>
                  <th>Campaña</th>
                  <th>Voluntario Asignado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody id="tabla-turnos-body">
                {filteredTurnos.length === 0 ? (
                  <tr><td colSpan={6} style={{ textAlign: 'center' }}>No hay turnos registrados para esta selección.</td></tr>
                ) : (
                  filteredTurnos.map((turno, i) => (
                    <tr key={i}>
                      <td style={{ textTransform: 'capitalize' }}>{formatDate(turno.dia)}</td>
                      <td>{turno.hora_inicio}</td>
                      <td>{turno.hora_fin}</td>
                      <td>{getCampanaNombre(turno.id_campana)}</td>
                      <td>{getVoluntarioDisplay(turno.id_voluntario)}</td>
                      <td>
                        {tienePermiso('EDITAR_TURNOS') && (
                          <div>
                            <button onClick={() => handleDelete(turno.id)} className="btn btn-danger btn-sm">Borrar</button>
                          </div>
                        )}
                        {tienePermiso('INCIDENCIAS') && (
                          <div>
                            <Link to={`/turnos/incidencia?idTurno=${turno.id}&idCampana=${turno.id_campana}`} className="btn btn-info btn-sm btn-incidence">Incidencia</Link>
                          </div>
                        )}
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
