package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;

import java.util.List;


public interface VoluntariosRepository extends JpaRepository<VoluntarioBaseEntity, Integer> {
    @Query("SELECT v FROM VoluntarioFisicoEntity v")
    List<VoluntarioFisicoEntity> findAllFisicos();

    @Query("SELECT v FROM VoluntarioEntidadEntity v")
    List<VoluntarioEntidadEntity> findAllEntidades();
}