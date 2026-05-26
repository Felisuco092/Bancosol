package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.TiendaEntity;

import java.util.List;
import java.util.Set;

public interface TiendasRepository extends JpaRepository<TiendaEntity, Integer>{
    @Query("select t from TiendaEntity t where t.localidad like concat('%',:local,'%')")
    public List<TiendaEntity> filtroLocalidad(@Param("local")String local);

    @Query("select t from TiendaEntity t where t.localidad like concat('%',:local,'%') and t.cadena.id = :idCad")
    public List<TiendaEntity> filtroLocalidadCadena(@Param("local")String local, @Param("idCad")Integer idCad);

    @Query("select distinct t.localidad from TiendaEntity t")
    public Set<String> getLocalidades();

    @Query("select t from TiendaEntity t join t.participaciones p where p.coordinador.id = :idCoord")
    public List<TiendaEntity> getAllCoord(@Param("idCoord")Integer idCoord);

    @Query("select t from TiendaEntity t where t.capitan.id = :idCapi")
    public List<TiendaEntity> getAllCapi(@Param("idCapi")Integer idCapi);

    @Query("select t from TiendaEntity t join t.participaciones p where (t.localidad like concat('%',:local,'%') and p.coordinador.id = :idCoord)")
    public List<TiendaEntity> filtroLocalidadCoord(@Param("local")String local, @Param("idCoord")Integer idCoord);

    @Query("select t from TiendaEntity t join t.participaciones p where (t.localidad like concat('%',:local,'%') and t.cadena.id = :idCad) and p.coordinador.id = :idCoord")
    public List<TiendaEntity> filtroLocalidadCadenaCoord(@Param("local")String local, @Param("idCad")Integer idCad, @Param("idCoord")Integer idCoord);

    @Query("select t from TiendaEntity t where (t.localidad like concat('%',:local,'%')) and t.capitan.id = :idCapi")
    public List<TiendaEntity> filtroLocalidadCapi(@Param("local")String local, @Param("idCapi")Integer idCapi);

    @Query("select t from TiendaEntity t where (t.localidad like concat('%',:local,'%') and t.cadena.id = :idCad) and t.capitan.id = :idCapi")
    public List<TiendaEntity> filtroLocalidadCadenaCapi(@Param("local")String local, @Param("idCad")Integer idCad, @Param("idCapi")Integer idCapi);
}
