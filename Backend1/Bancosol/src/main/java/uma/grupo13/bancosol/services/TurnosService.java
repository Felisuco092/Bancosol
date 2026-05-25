package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.dao.TurnoRepository;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.dto.TurnoDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.mappers.TurnoMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TurnosService {
    private final TurnoRepository turnoRepository;
    private final TurnoMapper turnoMapper;
    private final CampanaRepository campanaRepo;
    private final TiendasRepository tiendaRepo;
    private final VoluntariosRepository voluntariosRepo;

    public List<TurnoDTO> listarTurnos() {
        List<TurnoEntity> lista= turnoRepository.findAll();
        return turnoMapper.toDTOList(lista);
    }

    public TurnoDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        TurnoEntity turno = turnoRepository.getReferenceById(id);
        return turnoMapper.toDTO(turno);
    }

    public void borrarTurnoId(Integer id) {
        turnoRepository.deleteById(id);
    }

    public void guardarTurno(String tipoTurno, String fechaStr, String horaInicioStr, String horaFinStr,
                             Integer idCampana, Integer idTienda, Integer idVoluntario) throws Exception {
        TurnoEntity newTurno = new TurnoEntity();
        newTurno.setTipoTurno(tipoTurno);

        if (fechaStr != null && !fechaStr.isEmpty()) {
            newTurno.setDia(LocalDate.parse(fechaStr));
        }
        if (horaInicioStr != null && !horaInicioStr.isEmpty()) {
            newTurno.setHoraInicio(LocalTime.parse(horaInicioStr));
        }
        if (horaFinStr != null && !horaFinStr.isEmpty()) {
            newTurno.setHoraFin(LocalTime.parse(horaFinStr));
        }

        CampanaEntity campana = campanaRepo.getReferenceById(idCampana);
        TiendaEntity tienda = tiendaRepo.getReferenceById(idTienda);
        VoluntarioBaseEntity voluntario = voluntariosRepo.getReferenceById(idVoluntario);

        if (campana == null || tienda == null || voluntario == null) {
            throw new Exception("Campaña, Tienda o Voluntario no encontrado");
        }

        newTurno.setCampana(campana);
        newTurno.setTienda(tienda);
        newTurno.setVoluntario(voluntario);

        turnoRepository.save(newTurno);
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

    public void deleteAll(List<TurnoDTO> turnos) {
        for(TurnoDTO t: turnos){
            turnoRepository.deleteById(t.getId());
        }
    }
}
