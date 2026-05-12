import { useState, useEffect } from 'react'
import { fetchData } from '../services/api'
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
    const rol = roles.find(r => Number(r.id) === Number(id))
    return rol ? rol.nombre : 'Desconocido'
  }

  return (
    <>
      <header className="header">
        <h1>Gestión de Usuarios</h1>
        <button className="btn btn-primary">+ Crear Usuario</button>
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
                  <td><span className={`badge-rol badge-${quitarTildes(rolName.toLowerCase())}`}>{rolName}</span></td>
                  <td>{u.area_asignada}</td>
                  <td>
                    <button className="btn btn-primary btn-sm">Editar</button>
                    <button className="btn btn-danger btn-sm">Baja</button>
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
