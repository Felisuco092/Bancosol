package uma.grupo13.bancosol.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class TiendaMapper extends MapperDTO<TiendaDTO, TiendaEntity> {
    private final CadenaMapper cadenaMapper;
    private final UsuarioMapper usuarioMapper;

    @Override
    public TiendaDTO toDTO(TiendaEntity entity) {
        if (entity == null) return null;
        TiendaDTO dto = new TiendaDTO();
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setLocalidad(entity.getLocalidad());
        dto.setDomicilio(entity.getDomicilio());
        dto.setCPostal(entity.getCPostal());
        dto.setZonaGeografica(entity.getZonaGeografica());
        dto.setCadena(cadenaMapper.toDTO(entity.getCadena()));
        dto.setCapitan(usuarioMapper.toDTO(entity.getCapitan()));
        dto.setResponsableTienda(usuarioMapper.toDTO(entity.getResponsableTienda()));
        for(ParticipaEntity p : entity.getParticipaciones()){
            dto.getCampanas().add(p.getId().getIdCampana());
        }
        return dto;
    }
}
