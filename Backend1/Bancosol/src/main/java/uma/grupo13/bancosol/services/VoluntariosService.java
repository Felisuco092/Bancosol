package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class VoluntariosService {
    private final VoluntariosRepository voluntariosRepository;

    public List<VoluntarioBaseEntity> listarVoluntarios() {
        return voluntariosRepository.findAll();
    }

    public List<String> findLocalidadesDistintas() {
        return voluntariosRepository.findLocalidadesDistintas();
    }

    public List<VoluntarioBaseEntity> findAllByLocalidad(String localidad) {
        return voluntariosRepository.findAllByLocalidad(localidad);
    }

    public List<VoluntarioBaseEntity> findBaseFisicos(String localidad) {
        return voluntariosRepository.findBaseFisicos(localidad);
    }

    public List<VoluntarioBaseEntity> findBaseEntidades(String localidad) {
        return voluntariosRepository.findBaseEntidades(localidad);
    }

    public List<VoluntarioBaseEntity> findPendientes(String localidad) {
        return voluntariosRepository.findPendientes(localidad);
    }

    public VoluntarioBaseEntity buscarPorId(Integer id) {
        return voluntariosRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        voluntariosRepository.deleteById(id);
    }

    public void guardarVoluntario(VoluntarioBaseEntity voluntario) {
        voluntariosRepository.save(voluntario);
    }

    public int countTotalPersonasVoluntarias() {
        return voluntariosRepository.countTotalPersonasVoluntarias();
    }
}
