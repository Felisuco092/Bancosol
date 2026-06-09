/**
 * Clase que representa un voluntario tipo entidad dentro del sistema. Es subclase de voluntario_base
 *
 * Autores:
 * - IA Generativa: 100%
 */
package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "voluntario_entidad")
@PrimaryKeyJoinColumn(name = "id_voluntario")
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioEntidadEntity extends VoluntarioBaseEntity {

    @Column(columnDefinition = "TEXT")
    private String nombreAsociacion;

    private Integer nVoluntarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable_entidad")
    private UsuarioEntity responsableEntidad;

}
