package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.mappers.UsuarioMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuariosService {
    private final UserRepository userRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDTO autheticate(String username, String password) {
        UsuarioEntity user = userRepository.autheticate(username, password);
        return usuarioMapper.toDTO(user);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioEntity> lista= userRepository.findAll();
        return usuarioMapper.toDTOList(lista);
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

    public void borrarUsuario(UsuarioEntity usuario) {
        userRepository.delete(usuario);
    }

    public void guardarUsuario(UsuarioEntity usuario) {
        userRepository.save(usuario);
    }
}
