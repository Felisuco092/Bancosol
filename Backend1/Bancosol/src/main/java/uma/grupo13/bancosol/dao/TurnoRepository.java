package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uma.grupo13.bancosol.entity.TurnoEntity;

import java.util.List;

public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {
    List<TurnoEntity> findByVoluntarioId(Integer idVoluntario);
}
