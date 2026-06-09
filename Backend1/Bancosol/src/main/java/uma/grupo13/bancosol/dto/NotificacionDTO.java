/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 */

package uma.grupo13.bancosol.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NotificacionDTO {
    private Integer id;
    private Integer usuarioDestinoId;
    private LocalDateTime fechaCreacion;
    private String asunto;
    private String mensaje;

    public String getFechaFormateada() {
        if (fechaCreacion == null) return "";
        return fechaCreacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
