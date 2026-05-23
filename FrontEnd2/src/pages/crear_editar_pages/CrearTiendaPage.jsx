import { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { fetchData, postData } from '../../services/api'

export default function CrearTiendaPage() {
  const [cadenas, setCadenas] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    fetchData('cadenas').then(setCadenas).catch(console.error)
  }, [])

  function handleSubmit(e) {
    e.preventDefault()
    const formData = new FormData(e.target)
    const newTienda = {
      id_cadena: Number(formData.get('cadena[]')),
      descripcion: formData.get('descripcion'),
      localidad: formData.get('Localidad'),
      domicilio: formData.get('domicilio'),
      c_postal: formData.get('CPostal'),
      zona_geografica: formData.get('ZGeo'),
      id_capitan: null
    }

    postData('tiendas', newTienda)
      .then(() => {
        alert('Tienda creada con éxito')
        navigate('/tiendas')
      })
      .catch(err => {
        console.error('Error:', err)
        alert('No se pudo conectar con el servidor')
      })
  }

  return (
    <div className="form-page">
      <h1>Crear Tienda</h1>
      <div className="formulario">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="cadena">Cadena</label>
            <select name="cadena[]" id="cadena" required>
              <option value="">-- Seleccione una cadena --</option>
              {cadenas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre}</option>
              ))}
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="descripcion">Especificación</label>
            <input type="text" name="descripcion" id="descripcion" required />
          </div>
          <div className="form-group">
            <label htmlFor="Localidad">Localidad</label>
            <input type="text" name="Localidad" id="Localidad" required />
          </div>
          <div className="form-group">
            <label htmlFor="domicilio">Domicilio</label>
            <input type="text" name="domicilio" id="domicilio" required />
          </div>
          <div className="form-group">
            <label htmlFor="CPostal">Código Postal</label>
            <input type="text" name="CPostal" id="CPostal" required />
          </div>
          <div className="form-group">
            <label htmlFor="ZGeo">Zona Geográfica</label>
            <input type="text" name="ZGeo" id="ZGeo" required />
          </div>
          <div className="form-actions">
            <button type="submit" className="btn btn-primary">Crear Tienda</button>
            <Link to="/tiendas" className="btn btn-secondary">Cancelar</Link>
          </div>
        </form>
      </div>
    </div>
  )
}
