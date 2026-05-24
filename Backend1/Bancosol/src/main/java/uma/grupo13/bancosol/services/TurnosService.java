package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.TurnoRepository;
import uma.grupo13.bancosol.dto.TurnoDTO;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.mappers.TurnoMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnosService {
    private final TurnoRepository turnoRepository;
    private final TurnoMapper turnoMapper;

    public List<TurnoDTO> listarTurnos() {
        List<TurnoEntity> lista= turnoRepository.findAll();
        return turnoMapper.toDTOList(lista);
    }

    public TurnoDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        TurnoEntity turno = turnoRepository.getReferenceById(id);
        return turnoMapper.toDTO(turno);
    }

    public void borrarTurno(TurnoEntity turno) {
        turnoRepository.delete(turno);
    }

    public void guardarTurno(TurnoEntity turno) {
        turnoRepository.save(turno);
    }

    public List<TurnoDTO> filtrarTurnos(Integer idCampana, Integer idTienda) {
        List<TurnoEntity> lista= turnoRepository.filtrarTurnos(idCampana, idTienda);
        return turnoMapper.toDTOList(lista);
    }

    public List<TurnoDTO> findByVoluntarioId(Integer id) {
        if (id==null) {return null;}
        List<TurnoEntity> lista= turnoRepository.findByVoluntarioId(id);
        return turnoMapper.toDTOList(lista);
    }

    public void deleteAll(List<TurnoEntity> turnos) {
        turnoRepository.deleteAll(turnos);
    }
}
