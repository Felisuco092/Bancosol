package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.entity.ParticipaEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipaService {
    private final ParticipaRepository participaRepository;

    public List<ParticipaEntity> findByIdTienda(Integer id) {
        return participaRepository.findByIdTienda(id);
    }

    public void borrarParticipacion(ParticipaEntity participa) {
        participaRepository.delete(participa);
    }

    public void deleteAll(List<ParticipaEntity> participaciones) {
        participaRepository.deleteAll(participaciones);
    }

    public void guardarParticipacion(ParticipaEntity participa) {
        participaRepository.save(participa);
    }
}
