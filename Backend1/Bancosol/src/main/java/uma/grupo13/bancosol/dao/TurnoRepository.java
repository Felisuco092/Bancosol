package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.TurnoEntity;

import java.util.List;

public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {
    List<TurnoEntity> findByVoluntarioId(Integer idVoluntario);

    @Query("select t from TurnoEntity t where t.campana.id = :idCampana")
    public List<TurnoEntity> filtrarTurnosPorCampana(@Param("idCampana") Integer idTienda);

    @Query("select t from TurnoEntity  t where t.tienda.id = :idTienda")
    public List<TurnoEntity> filtrarTurnosPorTienda(@Param("idTienda") Integer idTienda);

    @Query("select t from TurnoEntity t where (:idCampana is null or t.campana.id = :idCampana) and (:idTienda is null or t.tienda.id = :idTienda)")
    public List<TurnoEntity> filtrarTurnos(@Param("idCampana") Integer idCampana, @Param("idTienda") Integer idTienda);

    @Query("select t from TurnoEntity t where (:idCampana is null or t.campana.id = :idCampana) and (:idTienda is null or t.tienda.id = :idTienda) and type(t.voluntario) = VoluntarioEntidadEntity and t.voluntario.responsableEntidad.id = :idResp")
    public List<TurnoEntity> filtrarTurnosRespEntd(@Param("idCampana") Integer idCampana, @Param("idTienda") Integer idTienda, @Param("idResp") Integer idResp);
}
