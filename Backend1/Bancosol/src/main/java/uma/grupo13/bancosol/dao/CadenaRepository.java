/**
 * Interfaz de repository para cadenas con querys.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 100%
 */
package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.CadenaEntity;

import java.util.List;

public interface CadenaRepository extends JpaRepository<CadenaEntity, Integer> {
    @Query("SELECT c FROM CadenaEntity c ORDER BY size(c.tiendas) DESC")
    public List<CadenaEntity> cadenasPorTiendas();
}
