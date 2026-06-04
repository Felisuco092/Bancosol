package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.NotificacionRepository;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.*;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.mappers.NotificacionMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionesService {
    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;
    private final UserRepository userRepository;


    public List<NotificacionDTO> listarNotificaciones() {
        List<NotificacionEntity> lista= notificacionRepository.findAll();
        return notificacionMapper.toDTOList(lista);
    }

    public NotificacionDTO getReferenceById(Integer id) {
        NotificacionEntity notificacion = notificacionRepository.getReferenceById(id);
        return notificacionMapper.toDTO(notificacion);
    }

    public void borrarNotificacionId(Integer id) {
        notificacionRepository.deleteById(id);
    }

    public void deleteAll(List<NotificacionDTO> notificaciones) {
        for(NotificacionDTO n: notificaciones){
            notificacionRepository.deleteById(n.getId());
        }
    }

    public void crearNotificacionColabYEnviar(String nombreColab, String apellidosColab, String nickCoordinador){

        List<UsuarioEntity> listaAdmins = userRepository.listaAdmins(); // traer todos los admins

        for(UsuarioEntity admin: listaAdmins){
            NotificacionEntity notificacionCrearColab = new NotificacionEntity();
            notificacionCrearColab.setAsunto("Nuevo colaborador por confirmar");
            notificacionCrearColab.setFechaCreacion(LocalDateTime.now()); // añadir fecha actual
            notificacionCrearColab.setMensaje("El usuario coordinador " + nickCoordinador +
                                        " ha creado un nuevo colaborador: "+ nombreColab + " " + apellidosColab +
                                        ", a espensas de la confirmación del administrador " + admin.getUsuario());
            notificacionCrearColab.setUsuarioDestino(admin);

            admin.getNotificaciones().add(notificacionCrearColab);
            notificacionRepository.save(notificacionCrearColab);
        }
    }

    public void crearNotificacionIncidencia(List<VoluntarioDTO> voluntariosImplicados, CampanaDTO campana, TurnoDTO turno, String mensaje,
                                            String asunto, String nombreUsuario){
        List<UsuarioEntity> listaAdmins = userRepository.listaAdmins();

        for(UsuarioEntity admin: listaAdmins){
            NotificacionEntity notificacionCrearIncidencia = new NotificacionEntity();
            notificacionCrearIncidencia.setAsunto("SE HA REGISTRADO UNA NUEVA INCIDENCIA: "+ asunto);
            notificacionCrearIncidencia.setFechaCreacion(LocalDateTime.now());
            notificacionCrearIncidencia.setMensaje("El usuario " + nombreUsuario + " ha registrado una incidencia en la campaña " +
                                                    campana.getNombre() + " en el turno comprendido entre las horas [" +
                                                    turno.getHoraInicio() + "-" + turno.getHoraFin() + "] por el siguiente motivo: "
                                                    + mensaje + "."+"\n" + "Se involucra a los siguientes voluntarios: " +
                                                    devolverStringVoluntarios(voluntariosImplicados));
            notificacionCrearIncidencia.setUsuarioDestino(admin);

            admin.getNotificaciones().add(notificacionCrearIncidencia);
            notificacionRepository.save(notificacionCrearIncidencia);
        }
    }

    // Método para devolver los usuarios implicados de tipo string para mostrar sus nombres en la notificación
    private String devolverStringVoluntarios(List<VoluntarioDTO> voluntariosIncidencia) {
        String mensaje = "";
        for(VoluntarioDTO voluntario: voluntariosIncidencia){
            mensaje += "- " + voluntario.getNombreDisplay() + "\n";
        }

        return mensaje;
    }
}
