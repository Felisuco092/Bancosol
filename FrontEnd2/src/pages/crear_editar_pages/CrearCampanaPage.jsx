import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData } from '../../services/api'

export default function CrearCampanaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [form, setForm] = useState({
    nombre: '',
    ano: '',
    dia_comienzo: '',
    dia_final: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        if (editando) {
          const c = await fetchData('campanas/' + id)
          setForm({
            nombre: c.nombre || '',
            ano: c.ano || '',
            dia_comienzo: c.dia_comienzo || '',
            dia_final: c.dia_final || ''
          })
        }
      } catch (err) {
        console.error(err)
      }
    }
    load()
  }, [])

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      if (editando) {
        await putData('campanas/' + id, form)
      } else {
        await postData('campanas', form)
      }
      alert(editando ? 'Campaña actualizada con éxito' : 'Campaña creada con éxito')
      navigate('/campanas')
    } catch (err) {
      console.error('Error:', err)
      alert('No se pudo conectar con el servidor')
    }
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Campaña' : 'Crear Campaña'}</h1>
      </header>

      <div className="formulario">
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="nombre">Nombre de la campaña<span className="required">*</span></label>
            <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="ano">Año<span className="required">*</span></label>
            <input type="number" name="ano" id="ano" value={form.ano} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="dia_comienzo">Fecha de inicio<span className="required">*</span></label>
            <input type="date" name="dia_comienzo" id="dia_comienzo" value={form.dia_comienzo} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="dia_final">Fecha de fin<span className="required">*</span></label>
            <input type="date" name="dia_final" id="dia_final" value={form.dia_final} onChange={handleChange} required />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Campaña'}</button>
            <Link to="/campanas" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
