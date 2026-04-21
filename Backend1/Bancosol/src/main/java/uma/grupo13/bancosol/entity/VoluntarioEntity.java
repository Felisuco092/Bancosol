package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VOLUNTARIO")
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre_entidad", nullable = false)
    private String nombreEntidad; // [cite: 611]

    @Column(name = "persona_fisica")
    private Boolean personaFisica = true;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    private String localidad;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "n_voluntarios")
    private Integer nVoluntarios = 1;

    @Column(columnDefinition = "TEXT")
    private String observaciones;
}
