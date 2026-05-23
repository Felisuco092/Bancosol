package uma.grupo13.bancosol.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificacionDTO {
    private Integer id;
    private Integer usuarioDestinoId;
    private LocalDateTime fechaCreacion;
    private String asunto;
    private String mensaje;
}
