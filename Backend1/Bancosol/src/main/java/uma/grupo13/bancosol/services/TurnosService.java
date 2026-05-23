package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.TurnoRepository;
import uma.grupo13.bancosol.entity.TurnoEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnosService {
    private final TurnoRepository turnoRepository;

    public List<TurnoEntity> listarTurnos() {
        return turnoRepository.findAll();
    }

    public TurnoEntity getReferenceById(Integer id) {
        return turnoRepository.getReferenceById(id);
    }

    public void borrarTurno(TurnoEntity turno) {
        turnoRepository.delete(turno);
    }

    public void guardarTurno(TurnoEntity turno) {
        turnoRepository.save(turno);
    }

    public List<TurnoEntity> filtrarTurnos(Integer idCampana, Integer idTienda) {
        return turnoRepository.filtrarTurnos(idCampana, idTienda);
    }

    public List<TurnoEntity> findByVoluntarioId(Integer id) {
        return turnoRepository.findByVoluntarioId(id);
    }

    public void deleteAll(List<TurnoEntity> turnos) {
        turnoRepository.deleteAll(turnos);
    }
}
