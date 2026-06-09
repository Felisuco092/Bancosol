package uma.grupo13.bancosol.mappers;
/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 */
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.RolDTO;
import uma.grupo13.bancosol.entity.RolEntity;

@Component
public class RolMapper extends MapperDTO<RolDTO, RolEntity> {
    @Override
    public RolDTO toDTO(RolEntity entity) {
        if (entity == null) return null;
        RolDTO dto = new RolDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
