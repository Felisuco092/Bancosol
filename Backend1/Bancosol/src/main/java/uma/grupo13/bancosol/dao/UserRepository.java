/**
 * Interfaz de repository para user con querys.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 10%
 * - Félix Jiménez Almanza: 45%
 * - Jorge Torres Sánchez: 25%
 * - IA Generativa: 20% (Usado para usar los Roles.* de la clase uma.grupo13.bancosol.services.utils.Roles)
 */
package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.utils.Roles;

import java.util.List;

public interface UserRepository extends JpaRepository<UsuarioEntity, Integer> {

    @Query("select u from UsuarioEntity u where u.usuario = :user and u.contrasena = :pwd")
    public UsuarioEntity autheticate (@Param("user")String username, @Param("pwd") String password);

    @Query("select u from UsuarioEntity u where u.rol.id = :rolId")
    public List<UsuarioEntity> findByRolId(@Param("rolId") Integer rolId);

    default List<UsuarioEntity> findCapitanes() {
        return findByRolId(Roles.CAPITAN);
    }

    default List<UsuarioEntity> findResponsablesEntidad() {
        return findByRolId(Roles.RESP_ENTIDAD);
    }

    default List<UsuarioEntity> findResponsablesTienda() {
        return findByRolId(Roles.RESP_TIENDA);
    }

    default List<UsuarioEntity> findCoordinadores() {
        return findByRolId(Roles.COORDINADOR);
    }

    @Query("select ad from UsuarioEntity ad where ad.id = 1")
    public List<UsuarioEntity> listaAdmins();

}
