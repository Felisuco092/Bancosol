package uma.grupo13.bancosol.mappers;

import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.CadenaDTO;
import uma.grupo13.bancosol.entity.CadenaEntity;

@Component
public class CadenaMapper extends MapperDTO<CadenaDTO, CadenaEntity> {
    @Override
    public CadenaDTO toDTO(CadenaEntity entity) {
        CadenaDTO dto = new CadenaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        dto.setNumeroTiendas(entity.getTiendas() != null ? entity.getTiendas().size() : 0);
        return dto;
    }
}
