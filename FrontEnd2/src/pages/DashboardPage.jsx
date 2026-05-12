import { useState, useEffect } from 'react'
import { fetchData } from '../services/api'
import { getActiveCampana, getNextCampana, getDaysRemaining } from '../utils/dateUtils'

export default function DashboardPage() {
  const [tiendas, setTiendas] = useState([])
  const [cadenas, setCadenas] = useState([])
  const [campanas, setCampanas] = useState([])
  const [voluntarioEntidad, setVoluntarioEntidad] = useState([])
  const [voluntarioFisico, setVoluntarioFisico] = useState([])

  useEffect(() => {
    Promise.all([
      fetchData('tiendas'),
      fetchData('cadenas'),
      fetchData('campanas'),
      fetchData('voluntario_entidad'),
      fetchData('voluntario_fisico')
    ]).then(([t, c, camp, ve, vf]) => {
      setTiendas(t)
      setCadenas(c)
      setCampanas(camp)
      setVoluntarioEntidad(ve)
      setVoluntarioFisico(vf)
    }).catch(err => console.error(err))
  }, [])

  const totalVoluntarios = voluntarioEntidad.reduce((s, v) => s + v.n_voluntarios, 0) + voluntarioFisico.length

  const cadenaCount = {}
  tiendas.forEach(t => {
    cadenaCount[t.id_cadena] = (cadenaCount[t.id_cadena] || 0) + 1
  })
  const topCadenas = Object.entries(cadenaCount)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 4)
    .map(([id, count]) => {
      const cadena = cadenas.find(c => Number(c.id) === Number(id))
      return { nombre: cadena ? cadena.nombre : 'Desconocida', tiendas: count }
    })

  const active = getActiveCampana(campanas)
  const next = getNextCampana(campanas)
  let daysText, daysLabel
  if (active) {
    const days = getDaysRemaining(active.dia_final)
    daysText = days > 0 ? days : '0'
    daysLabel = `Días restantes de "${active.nombre}"`
  } else if (next) {
    const daysUntil = getDaysRemaining(next.dia_comienzo)
    daysText = '---'
    daysLabel = `No hay campañas activas. Próxima: "${next.nombre}" en ${daysUntil} días`
  } else {
    daysText = '---'
    daysLabel = 'No hay campañas activas'
  }

  return (
    <>
      <header className="header">
        <h1>Cuadro de Mando</h1>
        <span>Bienvenido, Administrador</span>
      </header>
      <div className="dashboard-grid">
        <div className="card dashboard-card">
          <h3>Total de Tiendas</h3>
          <div className="dashboard-number">{tiendas.length}</div>
          <p className="dashboard-label">Tiendas registradas</p>
        </div>
        <div className="card dashboard-card">
          <h3>Total de Voluntarios Movilizados</h3>
          <p className="dashboard-number blue">{totalVoluntarios}</p>
          <p className="dashboard-label">voluntarios registrados</p>
        </div>
        <div className="card">
          <h3>Top Cadenas Participantes</h3>
          <table className="dashboard-table">
            <thead>
              <tr>
                <th>Cadena</th>
                <th>Tiendas</th>
              </tr>
            </thead>
            <tbody>
              {topCadenas.length === 0 ? (
                <tr><td colSpan={2}>No hay datos disponibles</td></tr>
              ) : (
                topCadenas.map((c, i) => (
                  <tr key={i}><td>{c.nombre}</td><td><strong>{c.tiendas}</strong></td></tr>
                ))
              )}
            </tbody>
          </table>
        </div>
        <div className="card dashboard-card">
          <h3>Días restantes</h3>
          <p className="dashboard-number blue">{daysText}</p>
          <p className="dashboard-label">{daysLabel}</p>
        </div>
      </div>
    </>
  )
}
