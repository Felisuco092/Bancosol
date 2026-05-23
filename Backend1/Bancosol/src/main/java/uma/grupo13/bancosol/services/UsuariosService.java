package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.entity.UsuarioEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuariosService {
    private final UserRepository userRepository;

    public UsuarioEntity autheticate(String username, String password) {
        return userRepository.autheticate(username, password);
    }

    public List<UsuarioEntity> listarUsuarios() {
        return userRepository.findAll();
    }

    public UsuarioEntity buscarPorId(Integer id) {
        if (id==null) {return null;}
        return userRepository.findById(id).orElse(null);
    }

    public UsuarioEntity getReferenceById(Integer id) {
        return userRepository.getReferenceById(id);
    }

    public List<UsuarioEntity> findCapitanes() {
        return userRepository.findCapitanes();
    }

    public void borrarUsuario(UsuarioEntity usuario) {
        userRepository.delete(usuario);
    }

    public void guardarUsuario(UsuarioEntity usuario) {
        userRepository.save(usuario);
    }
}
