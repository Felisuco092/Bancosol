package uma.grupo13.bancosol.mappers;

/**
 * Clase abstracta que pasa los Entities a DTO
 *
 * Autores:
 * - Eduardo Guzmán: 100% (Ejemplo de clase)
 */

import java.util.List;
import java.util.stream.Collectors;

public abstract class MapperDTO<DTOClass, EntityClass> {
    public abstract DTOClass toDTO (EntityClass entityClass);

    public List<DTOClass> toDTOList(List<EntityClass> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
