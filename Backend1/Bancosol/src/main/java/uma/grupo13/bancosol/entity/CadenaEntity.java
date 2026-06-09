/**
 * Clase que representa una cadena de tiendas dentro del sistema.
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
@Table(name = "cadena")
@Getter
@Setter
@NoArgsConstructor
public class CadenaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo; // Ej: CARR, MERC [cite: 489]

    @OneToMany(mappedBy = "cadena", orphanRemoval = true)
    private List<TiendaEntity> tiendas = new ArrayList<>();

    public void eliminarTiendas() {
        for (TiendaEntity tienda : tiendas) {
            tienda.getParticipaciones().clear();
            tienda.getTurnos().clear();
        }
        tiendas.clear();
    }
}
