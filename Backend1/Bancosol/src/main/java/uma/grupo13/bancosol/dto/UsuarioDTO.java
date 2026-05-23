package uma.grupo13.bancosol.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String email;
    private String telefono;
    private String areaAsignada;
    private RolDTO rol;
}
