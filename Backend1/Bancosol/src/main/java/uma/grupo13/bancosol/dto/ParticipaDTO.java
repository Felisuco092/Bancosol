/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 */

package uma.grupo13.bancosol.dto;

import lombok.Data;

@Data
public class ParticipaDTO {
    private Integer idCampana;
    private Integer idTienda;
    private CampanaDTO campana;
    private TiendaDTO tienda;
    private UsuarioDTO coordinador;
}
