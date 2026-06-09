/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 */

package uma.grupo13.bancosol.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CampanaDTO {
    private Integer id;
    private String nombre;
    private Integer ano;
    private LocalDate diaComienzo;
    private LocalDate diaFinal;
    private long tiempoRestante;
}
