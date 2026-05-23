package uma.grupo13.bancosol.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDTO {
    private Integer id;
    private String tipoTurno;
    private LocalDate dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private CampanaDTO campana;
    private VoluntarioDTO voluntario;
    private TiendaDTO tienda;
}
