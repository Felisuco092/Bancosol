/**
 * Clase que representa un voluntario tipo fisico dentro del sistema. Es subclase de voluntario_base
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
@Table(name = "voluntario_fisico")
@PrimaryKeyJoinColumn(name = "id_voluntario")
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioFisicoEntity extends VoluntarioBaseEntity {

    @Column(columnDefinition = "TEXT")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String apellidos;

}
