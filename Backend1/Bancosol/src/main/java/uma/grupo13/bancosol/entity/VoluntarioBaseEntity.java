/**
 * Clase que representa cualquier voluntario dentro del sistema.
 *
 * Autores:
 * - IA Generativa: 100%
 */
package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "voluntario_base")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    private String zonaGeografica;

    private String codigoPostal;

    private Boolean aprobado = false;

    @OneToMany(mappedBy = "voluntario")
    private List<TurnoEntity> turnos = new ArrayList<>();
}
