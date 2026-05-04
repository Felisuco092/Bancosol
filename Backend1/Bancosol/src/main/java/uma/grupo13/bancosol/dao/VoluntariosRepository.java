package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uma.grupo13.bancosol.entity.VoluntarioEntity;

public interface VoluntariosRepository extends JpaRepository<VoluntarioEntity, Integer> {
}
