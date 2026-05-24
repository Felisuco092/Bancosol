package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.NotificacionRepository;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.mappers.NotificacionMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionesService {
    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;


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

    public void deleteAll(List<NotificacionEntity> notificaciones) {
        notificacionRepository.deleteAll(notificaciones);
    }
}
