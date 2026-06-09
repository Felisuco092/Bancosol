import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData } from '../../services/api'
import { isInvalidDateRange, hasDateOverlap } from '../../utils/dateUtils'

export default function CrearCampanaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [cadenas, setCadenas] = useState([])
  const [todasCampanas, setTodasCampanas] = useState([])
  const [error, setError] = useState(null)
  const [cadenasSeleccion, setCadenasSeleccion] = useState([])
  const [form, setForm] = useState({
    nombre: '',
    ano: '',
    dia_comienzo: '',
    dia_final: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => {
      const next = { ...prev, [name]: value }
      if (name === 'dia_comienzo' && value) {
        next.ano = value.split('-')[0]
      }
      return next
    })
  }

  function toggleCadena(idCadena) {
    setCadenasSeleccion(prev =>
      prev.includes(idCadena)
        ? prev.filter(c => c !== idCadena)
        : [...prev, idCadena]
    )
  }

  useEffect(() => {
    async function load() {
      try {
        const [cads, camps] = await Promise.all([
          fetchData('cadenas'),
          fetchData('campanas')
        ])
        setCadenas(cads)
        setTodasCampanas(camps)

        if (editando) {
          const c = camps.find(camp => String(camp.id) === String(id))
          if (c) {
            setForm({
              nombre: c.nombre || '',
              ano: c.ano || '',
              dia_comienzo: c.dia_comienzo || '',
              dia_final: c.dia_final || ''
            })
          }
        }
      } catch (err) {
        setError(err.message)
      }
    }
    load()
  }, [id, editando])

  async function handleSubmit(e) {
    e.preventDefault()

    try {
        // Validaciones
      if (isInvalidDateRange(form.dia_comienzo, form.dia_final)) {
        throw new Error('La fecha de inicio no puede ser posterior a la fecha de fin.')
      }

      if (hasDateOverlap(form.dia_comienzo, form.dia_final, todasCampanas, id)) {
        throw new Error('Las fechas se solapan con una campaña existente.')
      }
      if (editando) {
        await putData('campanas/' + id, form)
        alert('Campaña actualizada con éxito')
      } else {
        const campanaRes = await postData('campanas', form)
        const campanaId = campanaRes.id

        if (cadenasSeleccion.length > 0) {
          const tiendasArrays = await Promise.all(
            cadenasSeleccion.map(cadId => fetchData('tiendas?id_cadena=' + cadId))
          )
          const todasTiendas = tiendasArrays.flat()
          await Promise.all(
            todasTiendas.map(t =>
              postData('participa', { id_campana: campanaId, id_tienda: t.id, id_coordinador: null })
            )
          )
        }

        alert('Campaña creada con éxito')
      }
      navigate('/campanas')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Campaña' : 'Crear Campaña'}</h1>
      </header>

      <div className="formulario">
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="nombre">Nombre de la campaña<span className="required">*</span></label>
            <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="dia_comienzo">Fecha de inicio<span className="required">*</span></label>
            <input type="date" name="dia_comienzo" id="dia_comienzo" value={form.dia_comienzo} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="dia_final">Fecha de fin<span className="required">*</span></label>
            <input type="date" name="dia_final" id="dia_final" value={form.dia_final} onChange={handleChange} required />
          </div>

          {!editando && (
            <div className="form-group">
              <label>Cadenas que participan:</label>
              <div className="checkbox-group">
                {cadenas.map(cad => (
                  <div key={cad.id} className="checkbox-item">
                    <input
                      type="checkbox"
                      checked={cadenasSeleccion.includes(String(cad.id))}
                      onChange={() => toggleCadena(String(cad.id))}
                    />
                    <label>{cad.nombre}</label>
                  </div>
                ))}
              </div>
            </div>
          )}

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Campaña'}</button>
            <Link to="/campanas" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
