package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import uma.grupo13.bancosol.entity.TiendaEntity;

public interface TiendasRepository extends JpaRepository<TiendaEntity, Integer>{
}
