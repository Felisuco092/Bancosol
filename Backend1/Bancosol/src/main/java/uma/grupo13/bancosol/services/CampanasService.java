package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CampanasService {
    private final CampanaRepository campanaRepository;
    private final ParticipaRepository participaRepository;

    public List<CampanaEntity> listarCampanas() {
        return campanaRepository.findAll();
    }

    public CampanaEntity buscarPorId(Integer id) {
        return campanaRepository.findById(id).orElse(null);
    }

    public CampanaEntity getReferenceById(Integer id) {
        return campanaRepository.getReferenceById(id);
    }

    public void borrarCampana(CampanaEntity campana) {
        campanaRepository.delete(campana);
    }

    public void guardarCampana(CampanaEntity campana) {
        campanaRepository.save(campana);
    }

    public Optional<CampanaEntity> findCampanaActiva() {
        return campanaRepository.findCampanaActiva();
    }

    public List<TiendaEntity> filtrarTiendasParticipaCampana(Integer idCampana) {
        return participaRepository.findTiendasByCampanaId(idCampana);
    }
}
