package uma.grupo13.bancosol.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private UsuarioDTO responsableTienda;
    private List<Integer> campanas = new ArrayList<>();

    public boolean participaEn(Integer idCampanaActual){
        return campanas.contains(idCampanaActual);
    }
}
