package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.NotificacionRepository;
import uma.grupo13.bancosol.entity.NotificacionEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificacionesService {
    private final NotificacionRepository notificacionRepository;

    public List<NotificacionEntity> listarNotificaciones() {
        return notificacionRepository.findAll();
    }

    public NotificacionEntity getReferenceById(Integer id) {
        return notificacionRepository.getReferenceById(id);
    }

    public void borrarNotificacion(NotificacionEntity notificacion) {
        notificacionRepository.delete(notificacion);
    }

    public void deleteAll(List<NotificacionEntity> notificaciones) {
        notificacionRepository.deleteAll(notificaciones);
    }
}
