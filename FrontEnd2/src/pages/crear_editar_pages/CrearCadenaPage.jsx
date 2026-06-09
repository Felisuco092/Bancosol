import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData } from '../../services/api'

export default function CrearCadenaPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [error, setError] = useState(null)
  const [form, setForm] = useState({
    nombre: '',
    codigo: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        if (editando) {
          const c = await fetchData('cadenas/' + id)
          setForm({
            nombre: c.nombre || '',
            codigo: c.codigo || ''
          })
        }
      } catch (err) {
        setError(err.message)
      }
    }
    load()
  }, [])

  async function handleSubmit(e) {
    e.preventDefault()
    try {
      if (editando) {
        await putData('cadenas/' + id, form)
      } else {
        await postData('cadenas', form)
      }
      alert(editando ? 'Cadena actualizada con éxito' : 'Cadena creada con éxito')
      navigate('/cadenas')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Cadena' : 'Crear Cadena'}</h1>
      </header>

      <div className="formulario">
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="nombre">Nombre<span className="required">*</span></label>
            <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="codigo">Código<span className="required">*</span></label>
            <input type="text" name="codigo" id="codigo" value={form.codigo} onChange={handleChange} required />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Cadena'}</button>
            <Link to="/cadenas" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
