package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Integer>{
    @Query("select ad from UsuarioEntity ad where ad.id = 1") // devolver todos los admins que existan en la bd
    public List<UsuarioEntity> listaAdmins();

}
