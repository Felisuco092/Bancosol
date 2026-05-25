package uma.grupo13.bancosol.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.ParticipaDTO;
import uma.grupo13.bancosol.entity.ParticipaEntity;

@Component
@AllArgsConstructor
public class ParticipaMapper extends MapperDTO<ParticipaDTO, ParticipaEntity> {
    private final CampanaMapper campanaMapper;
    private final TiendaMapper tiendaMapper;
    private final UsuarioMapper usuarioMapper;

    @Override
    public ParticipaDTO toDTO(ParticipaEntity entity) {
        if (entity == null) return null;
        ParticipaDTO dto = new ParticipaDTO();
        
        if (entity.getId() != null) {
            dto.setIdCampana(entity.getId().getIdCampana());
            dto.setIdTienda(entity.getId().getIdTienda());
        }
        
        dto.setCampana(campanaMapper.toDTO(entity.getCampana()));
        dto.setTienda(tiendaMapper.toDTO(entity.getTienda()));
        dto.setCoordinador(usuarioMapper.toDTO(entity.getCoordinador()));
        
        return dto;
    }
}
