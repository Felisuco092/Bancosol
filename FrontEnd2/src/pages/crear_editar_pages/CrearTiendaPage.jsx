import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData, deleteData } from '../../services/api'

export default function CrearTiendaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [cadenas, setCadenas] = useState([])

  const [capitanes, setCapitanes] = useState([])
  const [coordinadores, setCoordinadores] = useState([])

  const [responsablesTienda, setResponsablesTienda] = useState([])
  const [campanas, setCampanas] = useState([])
  const [participaSeleccion, setParticipaSeleccion] = useState([])
  const [form, setForm] = useState({
    nombre: '',
    localidad: '',
    domicilio: '',
    c_postal: '',
    zona_geografica: '',
    id_cadena: '',
    id_capitan: '',
    id_responsable_tienda: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        const [cads, users, respTienda, camps, coordinadores] = await Promise.all([
          fetchData('cadenas'),
          fetchData('usuarios?id_rol=2'),
          fetchData('usuarios?id_rol=5'),
          fetchData('campanas'),
          fetchData('usuarios?id_rol=3')
        ])
        setCadenas(cads)
        setCapitanes(users)
        setResponsablesTienda(respTienda)
        setCampanas(camps)
        setCoordinadores(coordinadores)

        if (editando) {
          const [t, participaciones] = await Promise.all([
            fetchData('tiendas/' + id),
            fetchData('participa?id_tienda=' + id)
          ])
          setForm({
            nombre: t.nombre || '',
            localidad: t.localidad || '',
            domicilio: t.domicilio || '',
            c_postal: t.c_postal || '',
            zona_geografica: t.zona_geografica || '',
            id_cadena: t.id_cadena || '',
            id_capitan: t.id_capitan || '',
            id_responsable_tienda: t.id_responsable_tienda || ''
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
    const aActualizar = seleccion.filter(p =>
      existentes.some(e => String(e.id_campana) === p.id_campana && String(e.id_coordinador) !== String(p.id_coordinador))
    )
    return Promise.all([
      ...aBorrar.map(p => deleteData('participa/' + p.id)),
      ...aCrear.map(p =>
        postData('participa', {
          id_tienda: tiendaId,
          id_campana: p.id_campana,
          id_coordinador: p.id_coordinador || null
        })
      ),
      ...aActualizar.map(p => {
        const idParticipa = existentes.find(e => String(e.id_campana) === p.id_campana).id
        return putData('participa/' + idParticipa, {
          id_tienda: tiendaId,
          id_campana: p.id_campana,
          id_coordinador: p.id_coordinador || null
        })
      })
    ])
  }

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      const tiendaData = { ...form, id_capitan: form.id_capitan || null, id_responsable_tienda: form.id_responsable_tienda || null }
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

  function handleSelectCoordinador(camp){
    let participacion = participaSeleccion.find(p => p.id_campana === String(camp.id))
    const coordinatores = coordinadores.map(coor => (
      <option>
        {coor.nombre} {coor.apellidos}
      </option>
    ))
    function handleChangeCoordinador(event) {
      const idCoordinador = event.target.value
      const idCampana = camp.id
      setParticipaSeleccion(prev => prev.map(p =>
        p.id_campana === String(idCampana)
          ? { ...p, id_coordinador: idCoordinador || null }
          : p
      ))
    }
    return (
      <select
        disabled={!participacion}
        value={participacion ? participacion.id_coordinador || '' : ''}
        onChange={handleChangeCoordinador}
        >
          <option value="">-- Sin coordinador --</option>
          {coordinadores.map(coor => (
            <option key={coor.id} value={coor.id}>
              {coor.nombre} {coor.apellidos}
            </option>
          ))}
      </select>
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
            <label htmlFor="id_responsable_tienda">Responsable de tienda:</label>
            <select name="id_responsable_tienda" id="id_responsable_tienda" value={form.id_responsable_tienda} onChange={handleChange}>
              <option value="">-- Seleccione un responsable --</option>
              {responsablesTienda.map(u => (
                <option key={u.id} value={u.id}>{u.nombre} {u.apellidos}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Campañas en las que participa:</label>
            <table>
                  <thead>
                    <tr>
                      <th>Campaña</th>
                      <th>Participa</th>
                      <th>Coordinador</th>
                    </tr>
                  </thead>
                  <tbody>
                    {campanas.map(camp => (
                <tr key={camp.id} >
                  <td><label>{camp.nombre}</label></td>
                  <td>
                      <input
                      type="checkbox"
                      checked={participaSeleccion.some(p => p.id_campana === String(camp.id))}
                      onChange={() => toggleCampana(String(camp.id))}
                    />
                  </td>
                  <td>
                    {handleSelectCoordinador(camp)}
                  </td>
                </tr>
                
              ))}
                  </tbody>
                </table>
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
