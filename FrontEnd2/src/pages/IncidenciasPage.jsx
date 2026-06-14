import { useState, useEffect } from 'react'
import { useSearchParams, useNavigate } from 'react-router-dom'
import { fetchData, postData } from '../services/api'
import { useAuth } from '../auth/useAuthHook'
import { Roles } from '../utils/constants'

export default function IncidenciasPage() {
    const { usuario } = useAuth()
    const [searchParams] = useSearchParams()
    const navigate = useNavigate()
    const idTurno = searchParams.get('idTurno')
    const idCampana = searchParams.get('idCampana')

    const [turno, setTurno] = useState(null)
    const [campana, setCampana] = useState(null)
    const [tienda, setTienda] = useState(null)
    const [voluntarios, setVoluntarios] = useState([])

    //Formulario
    const [asunto, setAsunto] = useState('')
    const [mensaje, setMensaje] = useState('')
    const [selectedVoluntarios, setSelectedVoluntarios] = useState([])

    useEffect(() => {
        if (!idTurno) return

        Promise.all([
            fetchData(`turnos/${idTurno}`),
            fetchData('voluntario_fisico'),
            fetchData('voluntario_entidad'),
            fetchData(`campanas/${idCampana}`)
        ]).then(([turnoData, vf, ve, campanaData]) => {
            setTurno(turnoData)
            setCampana(campanaData)

            const combined = [
                ...vf.map(v => ({ ...v, nombreDisplay: `${v.nombre} ${v.apellidos}`, tipo: 'FISICO' })),
                ...ve.map(v => ({ ...v, nombreDisplay: v.nombre_asociacion, tipo: 'ENTIDAD' }))
            ]
            setVoluntarios(combined)

            if (turnoData.id_voluntario) {
                setSelectedVoluntarios([String(turnoData.id_voluntario)])
            }

            if (turnoData.id_tienda) {
                fetchData(`tiendas/${turnoData.id_tienda}`).then(setTienda).catch(console.error)
            }
        }).catch(console.error)
    }, [idTurno, idCampana])

    function handleCheckboxChange(id) {
        setSelectedVoluntarios(prev =>
            prev.includes(id)
                ? prev.filter(v => v !== id)
                : [...prev, id]
        )
    }

    function handleSubmit(e) {
        e.preventDefault()

        const voluntariosInvolucrados = voluntarios.filter(v => selectedVoluntarios.includes(String(v.id_voluntario)))
            .map(v => `- ${v.nombreDisplay}`)
            .join('\n')

        const mensajeNotificacion = `El usuario ${usuario.usuario} ha registrado una incidencia en la campaña ${campana ? campana.nombre : 'Desconocida'} en el turno comprendido entre las horas [${turno ? turno.hora_inicio : '?'}-${turno ? turno.hora_fin : '?'}] en la tienda ${tienda ? tienda.nombre : 'Sin tienda'} por el siguiente motivo: ${mensaje}.\nSe involucra a los siguientes voluntarios:\n${voluntariosInvolucrados || 'Ninguno'}`

        const asuntoNotificacion = `SE HA REGISTRADO UNA NUEVA INCIDENCIA: ${asunto}`

        fetchData('usuarios?id_rol=' + Roles.ADMIN)
            .then(admins => {
                const promises = admins.map(admin =>
                    postData('notificaciones', {
                        id_usuario_destino: Number(admin.id),
                        fecha_creacion: new Date().toISOString(),
                        asunto: asuntoNotificacion,
                        mensaje: mensajeNotificacion
                    })
                )
                return Promise.all(promises)
            })
            .then(() => {
                navigate('/turnos')
            })
            .catch(err => {
                console.error('Error al reportar incidencia:', err)
                alert('Error al reportar la incidencia')
            })
    }

    if (!idTurno) {
        return (
            <main className="main-content">
                <header className="header">
                    <h1>Reporte de Incidencia</h1>
                </header>
                <div className="formulario">
                    <p className="text-center">No se ha especificado un turno.</p>
                    <div className="form-actions">
                        <button type="button" className="btn btn-secondary" onClick={() => navigate('/turnos')}>Volver</button>
                    </div>
                </div>
            </main>
        )
    }

    if (!turno || !campana) {
        return (
            <main className="main-content">
                <header className="header">
                    <h1>Reporte de Incidencia</h1>
                </header>
                <div className="formulario">
                    <p className="text-center">Cargando datos del turno y campaña...</p>
                </div>
            </main>
        )
    }

    return (
        <main className="main-content">
            <header className="header">
                <h1>Reporte de Incidencia</h1>
            </header>
            <div className="formulario">
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="asunto">Especifique el asunto de la incidencia:</label>
                        <input type="text" id="asunto" value={asunto} onChange={e => setAsunto(e.target.value)} required />
                    </div>

                    <div className="form-group">
                        <label htmlFor="mensaje">Especifique el motivo de la incidencia:</label>
                        <input type="text" id="mensaje" value={mensaje} onChange={e => setMensaje(e.target.value)} required />
                    </div>

                    <div className="form-group">
                        <label>Especifique los voluntarios/colaboradores implicados:</label>
                        <div className="checkbox-group" style={{ maxHeight: '200px', overflowY: 'auto', border: '1px solid var(--input-border)', padding: '10px', borderRadius: '4px' }}>
                            {voluntarios.map(v => {
                                const idVol = String(v.id_voluntario)
                                return (
                                    <div className="checkbox-item" key={idVol} style={{ display: 'flex', alignItems: 'center', marginBottom: '0.5rem' }}>
                                        <input
                                            type="checkbox"
                                            id={`vol-${idVol}`}
                                            value={idVol}
                                            checked={selectedVoluntarios.includes(idVol)}
                                            onChange={() => handleCheckboxChange(idVol)}
                                            style={{ width: 'auto', marginRight: '10px' }}
                                        />
                                        <label htmlFor={`vol-${idVol}`} style={{ marginBottom: 0, display: 'inline', fontWeight: 'normal' }}>{v.nombreDisplay}</label>
                                    </div>
                                )
                            })}
                        </div>
                    </div>

                    <div className="form-actions">
                        <button type="submit" className="btn btn-primary">Reportar incidencia</button>
                        <button type="button" className="btn btn-secondary" onClick={() => navigate('/turnos')}>Cancelar</button>
                    </div>
                </form>
            </div>
        </main>
    )
}
