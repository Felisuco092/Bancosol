package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.RolRepository;
import uma.grupo13.bancosol.entity.RolEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<RolEntity> listarRoles() {
        return rolRepository.findAll();
    }

    public RolEntity getReferenceById(Integer id) {
        return rolRepository.getReferenceById(id);
    }
}
