import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, deleteData } from '../services/api'

function formatTipo(esPersona, pendiente) {
  if (pendiente) {
    return <span className="badge badge-confirmar">Por confirmar</span>
  }
  return esPersona
    ? <span className="badge badge-persona">Persona Física</span>
    : <span className="badge badge-entidad">Entidad / Grupo</span>
}

export default function ColaboradoresPage() {
  const [rows, setRows] = useState([])
  const [filterTipo, setFilterTipo] = useState('all')
  const [filterLocalidad, setFilterLocalidad] = useState('all')

  useEffect(() => {
    Promise.all([
      fetchData('voluntario_base'),
      fetchData('voluntario_fisico'),
      fetchData('voluntario_entidad')
    ]).then(([voluntariosBase, voluntariosFisico, voluntariosEntidad]) => {
      const fisicoIds = new Set(voluntariosFisico.map(v => Number(v.id_voluntario)))
      const entidadIds = new Set(voluntariosEntidad.map(v => Number(v.id_voluntario)))

      function getVoluntarioType(id) {
        const nid = Number(id)
        if (fisicoIds.has(nid)) return 'fisico'
        if (entidadIds.has(nid)) return 'entidad'
        return 'desconocido'
      }

      const result = voluntariosBase.map(vb => {
        const type = getVoluntarioType(vb.id)
        if (type === 'fisico') {
          const f = voluntariosFisico.find(v => Number(v.id_voluntario) === Number(vb.id))
          return {
            ...vb,
            nombre: `${f.nombre} ${f.apellidos}`,
            persona_fisica: true,
            n_voluntarios: 1,
            localidad: f.localidad || vb.localidad || ''
          }
        }
        const e = voluntariosEntidad.find(v => Number(v.id_voluntario) === Number(vb.id))
        return {
          ...vb,
          nombre: e ? e.nombre_asociacion : 'Desconocido',
          persona_fisica: false,
          n_voluntarios: e ? e.n_voluntarios : 0,
          localidad: e ? e.localidad || '' : ''
        }
      })
      setRows(result)
    }).catch(console.error)
  }, [])

  async function handleDelete(id) {
    if (!confirm('¿Estás seguro de que deseas eliminar este colaborador? También se eliminarán sus turnos asociados.')) return
    try {
      const [fisicos, entidades, turnos] = await Promise.all([
        fetchData('voluntario_fisico?id_voluntario=' + id),
        fetchData('voluntario_entidad?id_voluntario=' + id),
        fetchData('turnos?id_voluntario=' + id)
      ])
      await Promise.all([
        ...fisicos.map(f => deleteData('voluntario_fisico/' + f.id)),
        ...entidades.map(e => deleteData('voluntario_entidad/' + e.id)),
        ...turnos.map(t => deleteData('turnos/' + t.id))
      ])
      await deleteData('voluntario_base/' + id)
      setRows(prev => prev.filter(r => String(r.id) !== String(id)))
      alert('Colaborador eliminado con éxito')
    } catch (err) {
      console.error('Error al eliminar colaborador:', err)
      alert('No se pudo eliminar el colaborador')
    }
  }

  const localidades = [...new Set(rows.map(r => r.localidad).filter(Boolean))]

  const filtered = rows.filter(row => {
    const matchTipo = filterTipo === 'all'
      || (filterTipo === 'confirmar' && row.aprobado === false || row.aprobado === 'false')
      || (filterTipo === 'true' && row.persona_fisica)
      || (filterTipo === 'false' && !row.persona_fisica)
    const matchLocalidad = filterLocalidad === 'all' || row.localidad === filterLocalidad
    return matchTipo && matchLocalidad
  })

  return (
    <>
      <header className="header">
        <h1>Gestión de Colaboradores</h1>
        <Link to="/colaboradores/crear" className="btn btn-primary">+ Nuevo Colaborador</Link>
      </header>
      <div className="card">
        <div className="filtros-grid">
          <div>
            <label htmlFor="filter-tipo">Tipo:</label>
            <select id="filter-tipo" value={filterTipo} onChange={e => setFilterTipo(e.target.value)}>
              <option value="all">Todos</option>
              <option value="false">Entidad / Grupo</option>
              <option value="true">Persona Física</option>
              <option value="confirmar">Por confirmar</option>
            </select>
          </div>
          <div>
            <label htmlFor="filter-localidad">Localidad:</label>
            <select id="filter-localidad" value={filterLocalidad} onChange={e => setFilterLocalidad(e.target.value)}>
              <option value="all">Todas</option>
              {localidades.map(loc => (
                <option key={loc} value={loc}>{loc}</option>
              ))}
            </select>
          </div>
        </div>

        <table>
          <thead>
            <tr>
              <th>Entidad / Nombre</th>
              <th>Tipo</th>
              <th>Localidad</th>
              <th>C.P.</th>
              <th>Voluntarios</th>
              <th>Observaciones</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((row, i) => {
              const pendiente = row.aprobado === false || row.aprobado === 'false'
              return (
                <tr key={i}>
                  <td>{row.nombre}</td>
                  <td>{formatTipo(row.persona_fisica, pendiente)}</td>
                  <td>{row.zona_geografica || ''}</td>
                  <td>{row.codigo_postal || ''}</td>
                  <td>{row.n_voluntarios}</td>
                  <td>{row.observaciones || ''}</td>
                  <td>
                    <Link to={`/colaboradores/editar/${row.id}`} className="btn btn-primary btn-sm">Editar</Link>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(row.id)}>Borrar</button>
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
