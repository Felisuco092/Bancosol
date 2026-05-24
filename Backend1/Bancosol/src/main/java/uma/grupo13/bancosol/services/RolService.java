package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.RolRepository;
import uma.grupo13.bancosol.dto.RolDTO;
import uma.grupo13.bancosol.entity.RolEntity;
import uma.grupo13.bancosol.mappers.RolMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class RolService {
    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    public List<RolDTO> listarRoles() {
        List<RolEntity> lista= rolRepository.findAll();
        return rolMapper.toDTOList(lista);
    }

    public RolDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        RolEntity rol = rolRepository.getReferenceById(id);
        return rolMapper.toDTO(rol);
    }
}
