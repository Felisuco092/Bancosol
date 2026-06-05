import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import { fetchData, putData, deleteData } from '../services/api'
import { quitarTildes } from '../utils/stringUtils'

export default function UsuariosPage() {
  const [usuarios, setUsuarios] = useState([])
  const [roles, setRoles] = useState([])

  useEffect(() => {
    Promise.all([
      fetchData('usuarios'),
      fetchData('roles')
    ]).then(([users, rls]) => {
      setUsuarios(users)
      setRoles(rls)
    }).catch(console.error)
  }, [])

  function getRolName(id) {
    const rol = roles.find(r => String(r.id) === String(id))
    return rol ? rol.nombre : 'Desconocido'
  }

  async function handleDelete(id) {
    if (!confirm('¿Estás seguro de que deseas dar de baja a este usuario? También se eliminarán sus notificaciones.')) return
    try {
      const [notificaciones, tiendas, entidades] = await Promise.all([
        fetchData('notificaciones?id_usuario_destino=' + id),
        fetchData('tiendas?id_responsable_tienda=' + id),
        fetchData('voluntario_entidad?id_responsable_entidad=' + id)
      ])
      await Promise.all([
        ...notificaciones.map(n => deleteData('notificaciones/' + n.id)),
        ...tiendas.map(t => putData('tiendas/' + t.id, { ...t, id_responsable_tienda: null })),
        ...entidades.map(e => putData('voluntario_entidad/' + e.id, { ...e, id_responsable_entidad: null }))
      ])
      await deleteData('usuarios/' + id)
      setUsuarios(prev => prev.filter(u => String(u.id) !== String(id)))
      alert('Usuario eliminado con éxito')
    } catch (err) {
      console.error('Error al eliminar usuario:', err)
      alert('No se pudo eliminar el usuario')
    }
  }

  return (
    <>
      <header className="header">
        <h1>Gestión de Usuarios</h1>
        <Link to="/usuarios/crear" className="btn btn-primary">+ Crear Usuario</Link>
      </header>
      <div className="card">
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Apellidos</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Rol</th>
              <th>Área Asignada (Municipio)</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {usuarios.map((u, i) => {
              const rolName = getRolName(u.id_rol)
              return (
                <tr key={i}>
                  <td>{u.nombre}</td>
                  <td>{u.apellidos}</td>
                  <td>{u.email}</td>
                  <td>{u.telefono}</td>
                  <td><span className={`badge-rol badge-${quitarTildes(rolName.toLowerCase()).split(' ').join('-')}`}>{rolName}</span></td>
                  <td>{u.area_asignada}</td>
                  <td>
                    <div><Link to={`/usuarios/editar/${u.id}`} className="btn btn-primary btn-sm">Editar</Link></div>
                    <div><button className="btn btn-danger btn-sm" onClick={() => handleDelete(u.id)}>Baja</button></div>
                  </td>
                </tr>
              )
            })}
          </tbody>
        </table>
      </div>
    </>
  )
}
