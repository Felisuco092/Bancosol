import { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import { fetchData, postData, putData } from '../../services/api'
import { useAuth } from '../../auth/useAuthHook'

export default function CrearColaboradorPage() {
  const { tienePermiso, usuario } = useAuth()
  const { id } = useParams()
  const editando = !!id
  const navigate = useNavigate()
  const [tipo, setTipo] = useState('')
  const [responsablesEntidad, setResponsablesEntidad] = useState([])
  const [aprobadoOriginal, setAprobadoOriginal] = useState(false)
  const [idRegistroVoluntario, setIdRegistroVoluntario] = useState(null)
  const [form, setForm] = useState({
    domicilio: '',
    zona_geografica: '',
    codigo_postal: '',
    observaciones: '',
    aprobado: false,
    nombre: '',
    apellidos: '',
    nombre_asociacion: '',
    n_voluntarios: '',
    id_responsable_entidad: ''
  })

  function handleChange(e) {
    const { name, value, type, checked } = e.target
    setForm(prev => ({ ...prev, [name]: type === 'checkbox' ? checked : value }))
  }

  useEffect(() => {
    async function load() {
      try {
        const respEnt = await fetchData('usuarios?id_rol=4')
        setResponsablesEntidad(respEnt)

        if (editando) {
          const vb = await fetchData('voluntario_base/' + id)
          let nombreFisico = '', apellidosFisico = '', nombreAsoc = '', nVol = ''
          let tipoDetectado = ''

          const vf = await fetchData('voluntario_fisico?id_voluntario=' + id).catch(() => [])
          if (vf && vf.length > 0) {
            tipoDetectado = 'fisico'
            nombreFisico = vf[0].nombre || ''
            apellidosFisico = vf[0].apellidos || ''
            setIdRegistroVoluntario(vf[0].id)
          }

          const ve = await fetchData('voluntario_entidad?id_voluntario=' + id).catch(() => [])
          let idRespEntidad = ''
          if (ve && ve.length > 0) {
            tipoDetectado = 'entidad'
            nombreAsoc = ve[0].nombre_asociacion || ''
            nVol = ve[0].n_voluntarios || ''
            idRespEntidad = ve[0].id_responsable_entidad || ''
            setIdRegistroVoluntario(ve[0].id)
          }

          setAprobadoOriginal(vb.aprobado === true || vb.aprobado === 'true')
          setTipo(tipoDetectado)
          setForm({
            domicilio: vb.domicilio || '',
            zona_geografica: vb.zona_geografica || '',
            codigo_postal: vb.codigo_postal || '',
            observaciones: vb.observaciones || '',
            aprobado: vb.aprobado === true || vb.aprobado === 'true',
            nombre: nombreFisico,
            apellidos: apellidosFisico,
            nombre_asociacion: nombreAsoc,
            n_voluntarios: nVol,
            id_responsable_entidad: idRespEntidad
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
        await putData('voluntario_base/' + id, {
          domicilio: form.domicilio,
          zona_geografica: form.zona_geografica,
          codigo_postal: form.codigo_postal,
          observaciones: form.observaciones,
          aprobado: form.aprobado
        })
        if (tipo === 'fisico') {
          await putData('voluntario_fisico/' + idRegistroVoluntario, {
            id_voluntario: id,
            nombre: form.nombre,
            apellidos: form.apellidos
          })
        } else if (tipo === 'entidad') {
          await putData('voluntario_entidad/' + idRegistroVoluntario, {
            id_voluntario: id,
            nombre_asociacion: form.nombre_asociacion,
            n_voluntarios: form.n_voluntarios,
            id_responsable_entidad: form.id_responsable_entidad || null
          })
        }
        alert('Colaborador actualizado con éxito')
      } else {
        const vb = await postData('voluntario_base', {
          domicilio: form.domicilio,
          zona_geografica: form.zona_geografica,
          codigo_postal: form.codigo_postal,
          observaciones: form.observaciones,
          aprobado: form.aprobado
        })
        const voluntarioId = vb.id
        if (tipo === 'fisico') {
          await postData('voluntario_fisico', {
            id_voluntario: voluntarioId,
            nombre: form.nombre,
            apellidos: form.apellidos
          })
        } else if (tipo === 'entidad') {
          await postData('voluntario_entidad', {
            id_voluntario: voluntarioId,
            nombre_asociacion: form.nombre_asociacion,
            n_voluntarios: form.n_voluntarios,
            id_responsable_entidad: form.id_responsable_entidad || null
          })
        }
        //Comprobamos si no es admin para avisar para que lo confirme un admin
        if (String(usuario.id_rol) !== "1") {
          const adminUsers = await fetchData('usuarios?id_rol=1')
          Promise.all(adminUsers.map(admin => {
            return postData('notificaciones', {
              id_usuario_destino: admin.id,
              asunto: 'Nuevo colaborador creado por ' + usuario.nombre + ' ' + usuario.apellidos + ' pendiente de confirmación',
              mensaje: `Nuevo colaborador "${tipo === 'fisico' ? form.nombre + ' ' + form.apellidos : form.nombre_asociacion}" creado por ${usuario.nombre} ${usuario.apellidos}. Por favor, revisa y confirma su colaboración.`,
              fecha_creacion: new Date().toISOString()
            })
          }))
          alert('Colaborador creado con éxito. Por favor, espera a que un administrador lo confirme.')
        } else {
          alert('Colaborador creado con éxito')
        }
      }
      navigate('/colaboradores')
    } catch (err) {
      console.error('Error:', err)
      alert('No se pudo conectar con el servidor')
    }
  }

  return (
    <>
      <header className="header">
        <h1>{editando ? 'Editar Colaborador' : 'Crear Colaborador'}</h1>
      </header>

      <div className="formulario">
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label htmlFor="domicilio">Domicilio<span className="required">*</span></label>
            <input type="text" name="domicilio" id="domicilio" value={form.domicilio} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="zona_geografica">Zona Geográfica<span className="required">*</span></label>
            <input type="text" name="zona_geografica" id="zona_geografica" value={form.zona_geografica} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="codigo_postal">Código Postal<span className="required">*</span></label>
            <input type="number" name="codigo_postal" id="codigo_postal" value={form.codigo_postal} onChange={handleChange} required min="0" />
          </div>

          <div className="form-group">
            <label htmlFor="observaciones">Observaciones</label>
            <textarea name="observaciones" id="observaciones" rows="3" value={form.observaciones} onChange={handleChange} />
          </div>

          {!editando && (
            <div className="form-group">
              <label htmlFor="tipo_colaborador">Tipo de Colaborador<span className="required">*</span></label>
              <select name="tipo_colaborador" id="tipo_colaborador" value={tipo} onChange={e => setTipo(e.target.value)} required>
                <option value="">-- Seleccione un tipo --</option>
                <option value="fisico">Persona Física</option>
                <option value="entidad">Entidad / Grupo</option>
              </select>
            </div>
          )}

          {tipo === 'fisico' && (
            <>
              <div className="form-group">
                <label htmlFor="nombre">Nombre<span className="required">*</span></label>
                <input type="text" name="nombre" id="nombre" value={form.nombre} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label htmlFor="apellidos">Apellidos<span className="required">*</span></label>
                <input type="text" name="apellidos" id="apellidos" value={form.apellidos} onChange={handleChange} required />
              </div>
            </>
          )}

          {tipo === 'entidad' && (
            <>
              <div className="form-group">
                <label htmlFor="nombre_asociacion">Nombre de Asociación<span className="required">*</span></label>
                <input type="text" name="nombre_asociacion" id="nombre_asociacion" value={form.nombre_asociacion} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label htmlFor="n_voluntarios">Número de Voluntarios<span className="required">*</span></label>
                <input type="number" name="n_voluntarios" id="n_voluntarios" value={form.n_voluntarios} onChange={handleChange} required min="0" />
              </div>
              <div className="form-group">
                <label htmlFor="id_responsable_entidad">Responsable de entidad:</label>
                <select name="id_responsable_entidad" id="id_responsable_entidad" value={form.id_responsable_entidad} onChange={handleChange}>
                  <option value="">-- Seleccione un responsable --</option>
                  {responsablesEntidad.map(u => (
                    <option key={u.id} value={u.id}>{u.nombre} {u.apellidos}</option>
                  ))}
                </select>
              </div>
            </>
          )}

          {tienePermiso('CONFIRMAR_COLABORADORES') && !aprobadoOriginal && (
            <div className="form-group">
              <label htmlFor="confirmar">Confirmar colaborador</label>
              <input type="checkbox" id="confirmar" name="aprobado" checked={form.aprobado} onChange={handleChange} />
            </div>
          )}

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">{editando ? 'Guardar cambios' : 'Crear Colaborador'}</button>
            <Link to="/colaboradores" className="btn btn-secondary">Cancelar</Link>
          </div>

        </form>
      </div>
    </>
  )
}
