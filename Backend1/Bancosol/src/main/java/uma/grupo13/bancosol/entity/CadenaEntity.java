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

    @OneToMany(mappedBy = "cadena")
    private List<TiendaEntity> tiendas = new ArrayList<>();
}
