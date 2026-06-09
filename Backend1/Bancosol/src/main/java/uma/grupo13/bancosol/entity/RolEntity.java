/**
 * Clase que representa un rol dentro del sistema.
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
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre; // Administrador, Coordinador, Capitan, etc. [cite: 734]

    @OneToMany(mappedBy = "rol")
    private List<UsuarioEntity> usuarios = new ArrayList<>();
}
