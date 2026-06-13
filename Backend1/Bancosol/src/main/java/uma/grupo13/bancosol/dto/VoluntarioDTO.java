/**
 * Clase que representa la DTO de los voluntarios.
 *
 * Autores:
 * - IA Generativa: 85%
 * - Félix Jiménez Almanza: 15%
 */

package uma.grupo13.bancosol.dto;

import lombok.Data;

@Data
public class VoluntarioDTO {
    private Integer id;
    private String domicilio;
    private String zonaGeografica;
    private String codigoPostal;
    private Boolean aprobado = false;
    private String tipo; // "FISICO" or "ENTIDAD"
    
    // Fisico specific
    private String nombre;
    private String apellidos;
    
    // Entidad specific
    private String nombreAsociacion;
    private Integer nVoluntarios;
    private UsuarioDTO responsableEntidad;

    // Helper for table display
    private String nombreDisplay;
    private int numeroVoluntariosDisplay;
}
