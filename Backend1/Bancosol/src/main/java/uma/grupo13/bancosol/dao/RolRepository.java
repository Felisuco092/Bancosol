/**
 * Interfaz de repository para roles.
 *
 * Autores:
 * - Jorge Torres Sánchez: 100%
 */
package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uma.grupo13.bancosol.entity.RolEntity;

public interface RolRepository extends JpaRepository<RolEntity, Integer> {
}
