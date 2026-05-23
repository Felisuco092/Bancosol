import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData } from '../../services/api'

export default function CrearTiendaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [tienda, setTienda] = useState(null)
  const [cadenas, setCadenas] = useState([])
  const [capitanes, setCapitanes] = useState([])
  const [campanas, setCampanas] = useState([])
  const [participa, setParticipa] = useState([])

  useEffect(() => {
    Promise.all([
      fetchData('cadenas'),
      fetchData('usuarios'),
      fetchData('campanas')
    ]).then(([cads, users, camps]) => {
      setCadenas(cads)
      setCapitanes(users)
      setCampanas(camps)
    }).catch(console.error)
    if (editando) {
      fetchData('tiendas/' + id).then(t => {
        setTienda(t)
        if (t.campanas) {
          setParticipa(t.campanas.map(c => Number(c.id)))
        }
      }).catch(console.error)
    }
  }, [])

  function handleSubmit(e) {
    e.preventDefault()
    const formData = new FormData(e.target)
    const tiendaData = {
      id_cadena: Number(formData.get('id_cadena')),
      descripcion: formData.get('descripcion'),
      localidad: formData.get('localidad'),
      domicilio: formData.get('domicilio'),
      c_postal: Number(formData.get('c_postal')),
      zona_geografica: formData.get('zona_geografica'),
      id_capitan: formData.get('id_capitan') ? Number(formData.get('id_capitan')) : null,
      campanasParticipa: participa
    }
    if (editando) tiendaData.id = Number(id)

    postData('tiendas', tiendaData)
      .then(() => {
        alert(editando ? 'Tienda actualizada con éxito' : 'Tienda creada con éxito')
        navigate('/tiendas')
      })
      .catch(err => {
        console.error('Error:', err)
        alert('No se pudo conectar con el servidor')
      })
  }

  function toggleCampana(campanaId) {
    setParticipa(prev =>
      prev.includes(campanaId)
        ? prev.filter(id => id !== campanaId)
        : [...prev, campanaId]
    )
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Tienda' : 'Crear Tienda'}</h1>
      </header>
      <div className="formulario">
        <form onSubmit={handleSubmit}>
          {editando && <input type="hidden" name="id" value={id} />}
          <div className="form-group">
            <label htmlFor="descripcion">Nombre de la tienda:</label>
            <input type="text" name="descripcion" id="descripcion" defaultValue={tienda?.descripcion || ''} required />
          </div>
          <div className="form-group">
            <label htmlFor="localidad">Localidad:</label>
            <input type="text" name="localidad" id="localidad" defaultValue={tienda?.localidad || ''} required />
          </div>
          <div className="form-group">
            <label htmlFor="domicilio">Domicilio:</label>
            <input type="text" name="domicilio" id="domicilio" defaultValue={tienda?.domicilio || ''} required />
          </div>
          <div className="form-group">
            <label htmlFor="c_postal">Código Postal:</label>
            <input type="number" name="c_postal" id="c_postal" defaultValue={tienda?.c_postal || ''} required />
          </div>
          <div className="form-group">
            <label htmlFor="zona_geografica">Zona Geográfica:</label>
            <input type="text" name="zona_geografica" id="zona_geografica" defaultValue={tienda?.zona_geografica || ''} required />
          </div>
          <div className="form-group">
            <label htmlFor="id_cadena">Cadena:</label>
            <select name="id_cadena" id="id_cadena" defaultValue={tienda?.id_cadena || ''} required>
              <option value="">-- Seleccione una cadena --</option>
              {cadenas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="id_capitan">Capitán:</label>
            <select name="id_capitan" id="id_capitan" defaultValue={tienda?.id_capitan || ''}>
              <option value="">-- Seleccione un capitán --</option>
              {capitanes.map(u => (
                <option key={u.id} value={u.id}>{u.nombre} {u.apellidos}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label>Campañas en las que participa:</label>
            <div style={{ maxHeight: '200px', overflowY: 'auto', border: '1px solid var(--input-border)', padding: '10px', borderRadius: '4px' }}>
              {campanas.map(camp => (
                <div key={camp.id} style={{ display: 'flex', alignItems: 'center', marginBottom: '0.5rem' }}>
                  <input
                    type="checkbox"
                    checked={participa.includes(Number(camp.id))}
                    onChange={() => toggleCampana(Number(camp.id))}
                    style={{ width: 'auto', marginRight: '10px' }}
                  />
                  <label style={{ marginBottom: 0, display: 'inline', fontWeight: 'normal' }}>{camp.nombre}</label>
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
