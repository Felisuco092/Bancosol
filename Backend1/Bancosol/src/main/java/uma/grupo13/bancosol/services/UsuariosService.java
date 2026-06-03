package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.RolRepository;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.RolEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.mappers.NotificacionMapper;
import uma.grupo13.bancosol.mappers.UsuarioMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuariosService {
    private final UserRepository userRepository;
    private final RolRepository rolRepo;
    private final UsuarioMapper usuarioMapper;
    private final NotificacionMapper notificacionMapper;

    public UsuarioDTO autheticate(String username, String password) {
        UsuarioEntity user = userRepository.autheticate(username, password);
        return usuarioMapper.toDTO(user);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioEntity> lista= userRepository.findAll();
        return usuarioMapper.toDTOList(lista);
    }

    public List<UsuarioDTO> listarAdmins(){
        List<UsuarioEntity> listaAdmins = userRepository.listaAdmins();
        return usuarioMapper.toDTOList(listaAdmins);
    }

    public UsuarioDTO buscarPorId(Integer id) {
        if (id==null) {return null;}
        UsuarioEntity user = userRepository.findById(id).orElse(null);
        return usuarioMapper.toDTO(user);
    }

    public UsuarioDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        UsuarioEntity user = userRepository.getReferenceById(id);
        return usuarioMapper.toDTO(user);
    }

    public List<UsuarioDTO> findCapitanes() {
        List<UsuarioEntity> lista= userRepository.findCapitanes();
        return usuarioMapper.toDTOList(lista);
    }

    public List<UsuarioDTO> findResponsablesTienda() {
        List<UsuarioEntity> lista = userRepository.findResponsablesTienda();
        return usuarioMapper.toDTOList(lista);
    }

    public List<UsuarioDTO> findResponsablesEntidad() {
        List<UsuarioEntity> lista = userRepository.findResponsablesEntidad();
        return usuarioMapper.toDTOList(lista);
    }

    public List<UsuarioDTO> findCoordinadores() {
        List<UsuarioEntity> lista = userRepository.findCoordinadores();
        return usuarioMapper.toDTOList(lista);
    }

    public void borrarUsuario(Integer id) {
        UsuarioEntity usuario = userRepository.getReferenceById(id);
        usuario.deleteTiendas();
        usuario.deleteEntidadesComoResponsable();
        userRepository.delete(usuario);
    }

    public void guardarUsuario(Integer id, String nombre, String apellidos, String user, String email, String telefono, String area, String password, Integer idRol) {
        UsuarioEntity usuario;
        if (id != null) {
            usuario = userRepository.getReferenceById(id);
        } else {
            usuario = new UsuarioEntity();
        }

        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setUsuario(user);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setAreaAsignada(area);

        if (password != null && !password.isEmpty()) {
            usuario.setContrasena(password);
        }

        RolEntity rol = rolRepo.getReferenceById(idRol);
        usuario.setRol(rol);

        userRepository.save(usuario);
    }

    public List<NotificacionDTO> getNotificaciones(Integer id) {
        UsuarioEntity user= userRepository.findById(id).orElse(null);
        if(user!=null){
            List<NotificacionEntity> lista=user.getNotificaciones();
            return notificacionMapper.toDTOList(lista);
        }else{
            return null;
        }
    }

}
