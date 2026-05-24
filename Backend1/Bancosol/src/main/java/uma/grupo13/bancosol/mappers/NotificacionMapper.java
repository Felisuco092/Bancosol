package uma.grupo13.bancosol.mappers;

import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;

@Component
public class NotificacionMapper extends MapperDTO<NotificacionDTO, NotificacionEntity> {
    @Override
    public NotificacionDTO toDTO(NotificacionEntity entity) {
        if (entity == null) return null;
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(entity.getId());
        dto.setUsuarioDestinoId(entity.getUsuarioDestino() != null ? entity.getUsuarioDestino().getId() : null);
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setAsunto(entity.getAsunto());
        dto.setMensaje(entity.getMensaje());
        return dto;
    }
}
