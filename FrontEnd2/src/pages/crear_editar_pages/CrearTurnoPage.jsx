import { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { fetchData, postData } from '../../services/api'
import { useAuth } from '../../auth/useAuthHook'
import { Roles } from '../../utils/constants'

export default function CrearTurnoPage() {
  const { usuario } = useAuth()
  const navigate = useNavigate()
  const [campanas, setCampanas] = useState([])
  const [tiendas, setTiendas] = useState([])
  const [error, setError] = useState(null)
  const [voluntarios, setVoluntarios] = useState([])

  //State select tiendas dependiendo de la campaña seleccionada
  const [tiendasFiltradas, setTiendasFiltradas] = useState([])
  const [form, setForm] = useState({
    id_campana: '',
    id_tienda: '',
    id_voluntario: '',
    tipo_turno: '',
    dia: '',
    hora_inicio: '',
    hora_fin: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  function handleCampanaChange(event) {
    const campanaId = event.target.value
    setForm(prev => ({ ...prev, id_campana: campanaId, id_tienda: '' }))
    populateTiendas(campanaId)
  }
  async function populateTiendas(campanaId) {
    try {
      let participacionesCampana = []
      if (usuario.id_rol === Roles.COORDINADOR) {
        participacionesCampana = await fetchData(`participa?id_campana=${campanaId}&id_coordinador=${usuario.id}`)
      } else {
        participacionesCampana = await fetchData(`participa?id_campana=${campanaId}`)
      }
      const tiendasFiltradas = tiendas.filter(t => participacionesCampana.some(p => String(p.id_tienda) === String(t.id)))
      setTiendasFiltradas(tiendasFiltradas)
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => {
    async function load() {
      try {
        const [camps, tds, vb, vf, ve] = await Promise.all([
          fetchData('campanas'),
          fetchData('tiendas'),
          fetchData('voluntario_base'),
          fetchData('voluntario_fisico'),
          fetchData('voluntario_entidad')
        ])

        setCampanas(camps)
        setTiendas(tds)

        const aprobados = vb.filter(v => v.aprobado === true || v.aprobado === 'true')
        const combined = aprobados.map(v => {
          const fisico = vf.find(f => String(f.id_voluntario) === String(v.id))
          if (fisico) return { ...v, nombreDisplay: `${fisico.nombre} ${fisico.apellidos}` }
          const entidad = ve.find(e => String(e.id_voluntario) === String(v.id))
          if (entidad) return { ...v, nombreDisplay: `${entidad.nombre_asociacion} (${entidad.n_voluntarios})` }
          return { ...v, nombreDisplay: `Voluntario #${v.id}` }
        })
        setVoluntarios(combined)
      } catch (err) {
        setError(err.message)
      }
    }
    load()
  }, [])

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      // La fecha de inicio no puede ser posterior a la fecha de fin
      
      if(form.hora_inicio >= form.hora_fin) {
        throw new Error('La hora de inicio debe ser anterior a la hora de fin.')
      }

      //La fecha debe estar dentro de las fechas de la campaña
      const campana = campanas.find(c => String(c.id) === String(form.id_campana))
      if (form.dia < campana.dia_comienzo || form.dia > campana.dia_final) {
        throw new Error('La fecha del turno debe estar dentro de las fechas de la campaña seleccionada.')
      }

      await postData('turnos', form)
      alert('Turno creado con éxito')
      navigate('/turnos')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <>
      <header className="header">
        <h1>Crear Turno</h1>
      </header>

      <div className="formulario">
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="id_campana">Campaña<span className="required">*</span></label>
            <select name="id_campana" id="id_campana" value={form.id_campana} onChange={handleCampanaChange} required>
              <option value="">-- Seleccione Campaña --</option>
              {campanas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre} - {c.ano}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="id_tienda">Tienda<span className="required">*</span></label>
            <select name="id_tienda" id="id_tienda" value={form.id_tienda} onChange={handleChange} 
              disabled={!form.id_campana}
              required>
              <option value="">-- Seleccione Tienda --</option>
              {tiendasFiltradas.map(t => (
                <option key={t.id} value={t.id}>{t.nombre}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="id_voluntario">Voluntario<span className="required">*</span></label>
            <select name="id_voluntario" id="id_voluntario" value={form.id_voluntario} onChange={handleChange} required>
              <option value="">-- Seleccione Voluntario --</option>
              {voluntarios.map(v => (
                <option key={v.id} value={v.id}>{v.nombreDisplay}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="tipo_turno">Tipo de turno<span className="required">*</span></label>
            <input type="text" name="tipo_turno" id="tipo_turno" value={form.tipo_turno} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="dia">Día del turno<span className="required">*</span></label>
            <input type="date" name="dia" id="dia" value={form.dia} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="hora_inicio">Hora de inicio<span className="required">*</span></label>
            <input type="time" name="hora_inicio" id="hora_inicio" value={form.hora_inicio} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="hora_fin">Hora de fin<span className="required">*</span></label>
            <input type="time" name="hora_fin" id="hora_fin" value={form.hora_fin} onChange={handleChange} required />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">Crear Turno</button>
            <Link to="/turnos" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
