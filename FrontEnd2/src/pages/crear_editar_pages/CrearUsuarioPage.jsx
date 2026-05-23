import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData } from '../../services/api'

export default function CrearUsuarioPage() {
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [roles, setRoles] = useState([])
  const [form, setForm] = useState({
    nombre: '',
    apellidos: '',
    usuario: '',
    password: '',
    email: '',
    telefono: '',
    area_asignada: '',
    id_rol: ''
  })

  function handleChange(e) {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  useEffect(() => {
    async function load() {
      try {
        const rls = await fetchData('roles')
        setRoles(rls)

        if (editando) {
          const u = await fetchData('usuarios/' + id)
          setForm({
            nombre: u.nombre || '',
            apellidos: u.apellidos || '',
            usuario: u.usuario || '',
            password: '',
            email: u.email || '',
            telefono: u.telefono || '',
            area_asignada: u.area_asignada || '',
            id_rol: u.id_rol || ''
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
      const data = { ...form }
      if (editando && !data.password) {
        delete data.password
      }
      if (editando) {
        await putData('usuarios/' + id, data)
      } else {
        await postData('usuarios', data)
      }
      alert(editando ? 'Usuario actualizado con éxito' : 'Usuario creado con éxito')
      navigate('/usuarios')
    } catch (err) {
      console.error('Error:', err)
      alert('No se pudo conectar con el servidor')
    }
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Usuario' : 'Crear Usuario'}</h1>
      </header>

      <div className="formulario">
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="nombre">Nombre<span className="required">*</span></label>
            <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="apellidos">Apellidos<span className="required">*</span></label>
            <input type="text" name="apellidos" id="apellidos" value={form.apellidos} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="usuario">Usuario<span className="required">*</span></label>
            <input type="text" name="usuario" id="usuario" value={form.usuario} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="password">Contraseña{!editando && <span className="required">*</span>}</label>
            <input type="password" name="password" id="password" value={form.password} onChange={handleChange} required={!editando} />
          </div>

          <div className="form-group">
            <label htmlFor="email">Email<span className="required">*</span></label>
            <input type="text" name="email" id="email" value={form.email} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="telefono">Teléfono</label>
            <input type="tel" name="telefono" id="telefono" value={form.telefono} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label htmlFor="area_asignada">Área Asignada</label>
            <input type="text" name="area_asignada" id="area_asignada" value={form.area_asignada} onChange={handleChange} />
          </div>

          <div className="form-group">
            <label htmlFor="id_rol">Rol<span className="required">*</span></label>
            <select name="id_rol" id="id_rol" value={form.id_rol} onChange={handleChange} required>
              <option value="">-- Seleccione un rol --</option>
              {roles.map(r => (
                <option key={r.id} value={r.id}>{r.nombre}</option>
              ))}
            </select>
          </div>

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Usuario'}</button>
            <Link to="/usuarios" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
