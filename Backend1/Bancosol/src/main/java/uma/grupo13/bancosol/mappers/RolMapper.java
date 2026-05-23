package uma.grupo13.bancosol.mappers;

import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.RolDTO;
import uma.grupo13.bancosol.entity.RolEntity;

@Component
public class RolMapper extends MapperDTO<RolDTO, RolEntity> {
    @Override
    public RolDTO toDTO(RolEntity entity) {
        RolDTO dto = new RolDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }
}
