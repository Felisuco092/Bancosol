package uma.grupo13.bancosol.mappers;
/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 * -Nota: Para los DTO pedimos a la IA que nos generase unos dto y mappers similares a los que había en las carpetas (ejemplo de clase borrado tras esta petición)
 * basandose en los campos que utilizamos con las entidades en los jsp en la version previa. La implementación de los dto, tanto en sevices como en controller
 * y jsp se hizo de forma manual incluso cambiando algunas cosas que nos dio la IA porque no eran necesarias o estaban mal
 */
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.CadenaDTO;
import uma.grupo13.bancosol.entity.CadenaEntity;

@Component
public class CadenaMapper extends MapperDTO<CadenaDTO, CadenaEntity> {
    @Override
    public CadenaDTO toDTO(CadenaEntity entity) {
        if (entity == null) return null;
        CadenaDTO dto = new CadenaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setCodigo(entity.getCodigo());
        dto.setNumeroTiendas(entity.getTiendas() != null ? entity.getTiendas().size() : 0);
        return dto;
    }
}
