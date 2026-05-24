package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.dto.ParticipaDTO;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.mappers.ParticipaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipaService {
    private final ParticipaRepository participaRepository;
    private final ParticipaMapper participaMapper;

    public List<ParticipaDTO> findByIdTienda(Integer id) {
        List<ParticipaEntity> lista= participaRepository.findByIdTienda(id);
        return participaMapper.toDTOList(lista);
    }

    public List<ParticipaDTO> findByCoordinadorId(Integer id) {
        List<ParticipaEntity> lista= participaRepository.findByCoordinadorId(id);
        return participaMapper.toDTOList(lista);
    }

    public void deleteAll(List<ParticipaEntity> participaciones) {
        participaRepository.deleteAll(participaciones);
    }

    public void guardarParticipacion(ParticipaEntity participa) {
        participaRepository.save(participa);
    }

    public void guardarParticipaciones(List<ParticipaEntity> participaciones) {
        participaRepository.saveAll(participaciones);
    }
}
