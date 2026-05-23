package uma.grupo13.bancosol.dto;

import lombok.Data;

@Data
public class TiendaDTO {
    private Integer id;
    private String descripcion;
    private String localidad;
    private String domicilio;
    private String cPostal;
    private String zonaGeografica;
    private CadenaDTO cadena;
    private UsuarioDTO capitan;
    private boolean participaEnCampanaActual;
}
