package uma.grupo13.bancosol.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.TurnoDTO;
import uma.grupo13.bancosol.entity.TurnoEntity;

@Component
@AllArgsConstructor
public class TurnoMapper extends MapperDTO<TurnoDTO, TurnoEntity> {
    private final CampanaMapper campanaMapper;
    private final VoluntarioMapper voluntarioMapper;
    private final TiendaMapper tiendaMapper;

    @Override
    public TurnoDTO toDTO(TurnoEntity entity) {
        TurnoDTO dto = new TurnoDTO();
        dto.setId(entity.getId());
        dto.setTipoTurno(entity.getTipoTurno());
        dto.setDia(entity.getDia());
        dto.setHoraInicio(entity.getHoraInicio());
        dto.setHoraFin(entity.getHoraFin());
        dto.setCampana(campanaMapper.toDTO(entity.getCampana()));
        dto.setVoluntario(voluntarioMapper.toDTO(entity.getVoluntario()));
        dto.setTienda(tiendaMapper.toDTO(entity.getTienda()));
        return dto;
    }
}
