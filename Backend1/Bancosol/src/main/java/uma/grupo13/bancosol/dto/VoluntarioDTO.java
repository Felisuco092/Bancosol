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

    // Helper for table display
    private String nombreDisplay;
    private int numeroVoluntariosDisplay;
}
