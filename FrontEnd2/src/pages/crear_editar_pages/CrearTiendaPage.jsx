import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData, deleteData } from '../../services/api'

export default function CrearTiendaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [cadenas, setCadenas] = useState([])
  const [capitanes, setCapitanes] = useState([])
  const [campanas, setCampanas] = useState([])
  const [participaSeleccion, setParticipaSeleccion] = useState([])
  const [form, setForm] = useState({
    nombre: '',
    descripcion: '',
    localidad: '',
    domicilio: '',
    c_postal: '',
    zona_geografica: '',
    id_cadena: '',
    id_capitan: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        const [cads, users, camps] = await Promise.all([
          fetchData('cadenas'),
          fetchData('usuarios?id_rol=2'),
          fetchData('campanas')
        ])
        setCadenas(cads)
        setCapitanes(users)
        setCampanas(camps)

        if (editando) {
          const [t, participaciones] = await Promise.all([
            fetchData('tiendas/' + id),
            fetchData('participa?id_tienda=' + id)
          ])
          setForm({
            nombre: t.nombre || '',
            descripcion: t.descripcion || '',
            localidad: t.localidad || '',
            domicilio: t.domicilio || '',
            c_postal: t.c_postal || '',
            zona_geografica: t.zona_geografica || '',
            id_cadena: t.id_cadena || '',
            id_capitan: t.id_capitan || ''
          })
          setParticipaSeleccion(participaciones.map(p => ({
            id_campana: String(p.id_campana),
            id_coordinador: p.id_coordinador ? String(p.id_coordinador) : null
          })))
        }
      } catch (err) {
        console.error(err)
      }
    }
    load()
  }, [])

  async function syncParticipa(tiendaId, existentes, seleccion) {
    const aBorrar = existentes.filter(p =>
      !seleccion.some(s => s.id_campana === String(p.id_campana))
    )
    const aCrear = seleccion.filter(p =>
      !existentes.some(e => String(e.id_campana) === p.id_campana)
    )
    return Promise.all([
      ...aBorrar.map(p => deleteData('participa/' + p.id)),
      ...aCrear.map(p =>
        postData('participa', {
          id_tienda: tiendaId,
          id_campana: p.id_campana,
          id_coordinador: p.id_coordinador || null
        })
      )
    ])
  }

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      const tiendaData = { ...form, id_capitan: form.id_capitan || null }
      const tiendaId = editando
        ? await putData('tiendas/' + id, tiendaData).then(() => id)
        : await postData('tiendas', tiendaData).then(res => res.id)

      if (editando) {
        const existentes = await fetchData('participa?id_tienda=' + tiendaId)
        await syncParticipa(tiendaId, existentes, participaSeleccion)
      } else {
        await Promise.all(
          participaSeleccion.map(p =>
            postData('participa', {
              id_tienda: tiendaId,
              id_campana: p.id_campana,
              id_coordinador: p.id_coordinador || null
            })
          )
        )
      }

      alert(editando ? 'Tienda actualizada con éxito' : 'Tienda creada con éxito')
      navigate('/tiendas')
    } catch (err) {
      console.error('Error:', err)
      alert('No se pudo conectar con el servidor')
    }
  }

  function toggleCampana(idCampana) {
    setParticipaSeleccion(prev =>
      prev.some(p => p.id_campana === idCampana)
        ? prev.filter(p => p.id_campana !== idCampana)
        : [...prev, { id_campana: idCampana, id_coordinador: null }]
    )
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Tienda' : 'Crear Tienda'}</h1>
      </header>

      <div className="formulario">
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="nombre">Nombre de la tienda<span className="required">*</span></label>
            <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="localidad">Localidad<span className="required">*</span></label>
            <input type="text" name="localidad" id="localidad" value={form.localidad} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="domicilio">Domicilio<span className="required">*</span></label>
            <input type="text" name="domicilio" id="domicilio" value={form.domicilio} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="c_postal">Código Postal<span className="required">*</span></label>
            <input type="number" name="c_postal" id="c_postal" value={form.c_postal} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="zona_geografica">Zona Geográfica<span className="required">*</span></label>
            <input type="text" name="zona_geografica" id="zona_geografica" value={form.zona_geografica} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="id_cadena">Cadena<span className="required">*</span></label>
            <select name="id_cadena" id="id_cadena" value={form.id_cadena} onChange={handleChange} required>
              <option value="">-- Seleccione una cadena --</option>
              {cadenas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="id_capitan">Capitán:</label>
            <select name="id_capitan" id="id_capitan" value={form.id_capitan} onChange={handleChange}>
              <option value="">-- Seleccione un capitán --</option>
              {capitanes.map(u => (
                <option key={u.id} value={u.id}>{u.nombre} {u.apellidos}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Campañas en las que participa:</label>
            <div className="checkbox-group">
              {campanas.map(camp => (
                <div key={camp.id} className="checkbox-item">
                  <input
                    type="checkbox"
                    checked={participaSeleccion.some(p => p.id_campana === String(camp.id))}
                    onChange={() => toggleCampana(String(camp.id))}
                  />
                  <label>{camp.nombre}</label>
                </div>
              ))}
            </div>
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Tienda'}</button>
            <Link to="/tiendas" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
