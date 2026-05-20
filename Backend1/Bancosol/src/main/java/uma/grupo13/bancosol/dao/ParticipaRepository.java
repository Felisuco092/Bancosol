package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.ParticipaEntity;

import java.util.List;

public interface ParticipaRepository extends JpaRepository<ParticipaEntity, Integer> {
    @Query("select p from ParticipaEntity p where p.id.idTienda = :id ")
    public List<ParticipaEntity> findByIdTienda(@Param("id")Integer id);
}
