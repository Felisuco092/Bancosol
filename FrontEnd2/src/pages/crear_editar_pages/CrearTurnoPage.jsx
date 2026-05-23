import { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { fetchData, postData } from '../../services/api'

export default function CrearTurnoPage() {
  const navigate = useNavigate()
  const [campanas, setCampanas] = useState([])
  const [tiendas, setTiendas] = useState([])
  const [form, setForm] = useState({
    id_campana: '',
    id_tienda: '',
    tipo_turno: '',
    dia: '',
    hora_inicio: '',
    hora_fin: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        const [camps, tds] = await Promise.all([
          fetchData('campanas'),
          fetchData('tiendas')
        ])
        setCampanas(camps)
        setTiendas(tds)
      } catch (err) {
        console.error(err)
      }
    }
    load()
  }, [])

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      await postData('turnos', form)
      alert('Turno creado con éxito')
      navigate('/turnos')
    } catch (err) {
      console.error('Error:', err)
      alert('No se pudo conectar con el servidor')
    }
  }

  return (
    <>
      <header className="header">
        <h1>Crear Turno</h1>
      </header>

      <div className="formulario">
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="id_campana">Campaña<span className="required">*</span></label>
            <select name="id_campana" id="id_campana" value={form.id_campana} onChange={handleChange} required>
              <option value="">-- Seleccione Campaña --</option>
              {campanas.map(c => (
                <option key={c.id} value={c.id}>{c.nombre} - {c.ano}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="id_tienda">Tienda<span className="required">*</span></label>
            <select name="id_tienda" id="id_tienda" value={form.id_tienda} onChange={handleChange} required>
              <option value="">-- Seleccione Tienda --</option>
              {tiendas.map(t => (
                <option key={t.id} value={t.id}>{t.nombre}</option>
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
