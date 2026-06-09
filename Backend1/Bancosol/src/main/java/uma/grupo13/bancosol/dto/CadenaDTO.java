/**
 * Clase que representa la DTO de las cadenas.
 *
 * Autores:
 * -IA Generativa: 100%
 * -Nota: Para los DTO pedimos a la IA que nos generase unos dto y mappers similares a los que había en las carpetas(ejemplo de clase borrado tras esta petición) basandose en los campos que utilizamos con las entidades en los jsp en la version previa.
 * La implementación de los dto, tanto en sevices como en controller y jsp se hizo de forma manual incluso cambiando algunas cosas que nos dio la IA porque no eran necesarias o estaban mal
 */

package uma.grupo13.bancosol.dto;

import lombok.Data;

@Data
public class CadenaDTO {
    private Integer id;
    private String nombre;
    private String codigo;
    private int numeroTiendas;
}
