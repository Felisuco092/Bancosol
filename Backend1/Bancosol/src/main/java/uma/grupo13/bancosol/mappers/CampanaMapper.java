package uma.grupo13.bancosol.mappers;
/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 */
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.CampanaDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;

@Component
public class CampanaMapper extends MapperDTO<CampanaDTO, CampanaEntity> {
    @Override
    public CampanaDTO toDTO(CampanaEntity entity) {
        if (entity == null) return null;
        CampanaDTO dto = new CampanaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setAno(entity.getAno());
        dto.setDiaComienzo(entity.getDiaComienzo());
        dto.setDiaFinal(entity.getDiaFinal());
        dto.setTiempoRestante(entity.getTiempoRestante());
        return dto;
    }
}
