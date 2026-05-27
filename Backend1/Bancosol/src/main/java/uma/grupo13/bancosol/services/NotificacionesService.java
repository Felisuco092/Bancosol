package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.NotificacionRepository;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.NotificacionDTO;
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

        List<UsuarioEntity> listaAdmins = userRepository.listaAdmins(); // traer todos los admins c
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
}
