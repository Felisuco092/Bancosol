/**
 * Interfaz de repository para voluntarios tanto fisico como entidades con querys.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 5%
 * - Félix Jiménez Almanza: 55%
 * - Jorge Torres Sánchez: 25%
 * - IA Generativa: 15% (el query countTotalPersonasVoluntarias lo hizo la IA)
 */
package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;

import java.util.List;

// Función countTotalPersonasVoluntarias lo ha hecho Gemini
public interface VoluntariosRepository extends JpaRepository<VoluntarioBaseEntity, Integer> {

    @Query("SELECT v FROM VoluntarioBaseEntity v WHERE v.zonaGeografica LIKE CONCAT('%', :localidad, '%')")
    List<VoluntarioBaseEntity> findAllByLocalidad(@Param("localidad") String localidad);

    @Query("SELECT v FROM VoluntarioBaseEntity v WHERE TYPE(v) = VoluntarioFisicoEntity AND v.zonaGeografica LIKE CONCAT('%', :localidad, '%')")
    List<VoluntarioBaseEntity> findBaseFisicos(@Param("localidad") String localidad);

    @Query("SELECT v FROM VoluntarioBaseEntity v WHERE TYPE(v) = VoluntarioEntidadEntity AND v.zonaGeografica LIKE CONCAT('%', :localidad, '%')")
    List<VoluntarioBaseEntity> findBaseEntidades(@Param("localidad") String localidad);

    @Query("SELECT v FROM VoluntarioBaseEntity v WHERE (v.aprobado IS NULL OR v.aprobado = FALSE) AND v.zonaGeografica LIKE CONCAT('%', :localidad, '%')")
    List<VoluntarioBaseEntity> findPendientes(@Param("localidad") String localidad);

    @Query("SELECT DISTINCT v.zonaGeografica FROM VoluntarioBaseEntity v WHERE v.zonaGeografica IS NOT NULL")
    List<String> findLocalidadesDistintas();

    @Query("SELECT " +
           "(SELECT COUNT(vf) FROM VoluntarioFisicoEntity vf) + " +
           "(SELECT COALESCE(SUM(ve.nVoluntarios), 0) FROM VoluntarioEntidadEntity ve)")
    int countTotalPersonasVoluntarias();

    @Query("select v from VoluntarioEntidadEntity v where v.responsableEntidad.id = :idResp")
    List<VoluntarioBaseEntity> findAllResponsable(@Param("idResp") Integer idResp);

    @Query("SELECT v FROM VoluntarioEntidadEntity v WHERE (v.zonaGeografica LIKE CONCAT('%', :localidad, '%') and v.responsableEntidad.id = :idResp)")
    List<VoluntarioBaseEntity> findAllByLocalidadResponsable(@Param("localidad") String localidad, @Param("idResp") Integer idRes);

    @Query("SELECT v FROM VoluntarioEntidadEntity v WHERE (v.aprobado IS NULL OR v.aprobado = FALSE) AND (v.zonaGeografica LIKE CONCAT('%', :localidad, '%')) and v.responsableEntidad.id = :idResp")
    List<VoluntarioBaseEntity> findPendientesResponsable(@Param("localidad") String localidad, @Param("idResp") Integer idResp);
}
