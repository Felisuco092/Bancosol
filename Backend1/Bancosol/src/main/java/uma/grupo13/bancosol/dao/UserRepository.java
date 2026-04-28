package uma.grupo13.bancosol.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uma.grupo13.bancosol.entity.UsuarioEntity;

public interface UserRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("select u from UsuarioEntity u where u.usuario = :user and u.contrasena = :pwd")
    public UsuarioEntity autheticate (@Param("user")String username, @Param("pwd") String password);
}
