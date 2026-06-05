package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Integer>{
    @Query("select noti from NotificacionEntity noti where noti.usuarioDestino.id = :idUsuario")
    public List<NotificacionEntity> listarNotificacionesUsuario(@Param("idUsuario") Integer idUsuario);

}
