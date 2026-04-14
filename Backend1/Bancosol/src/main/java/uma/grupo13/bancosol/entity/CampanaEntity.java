package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAMPANA")
@Getter
@Setter
@NoArgsConstructor
public class CampanaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre; // Ej: Primavera, Gran Recogida [cite: 472, 473]

    @Column(nullable = false)
    private Integer ano;

    @Column(name = "dia_comienzo", nullable = false)
    private LocalDate diaComienzo;

    @Column(name = "dia_final", nullable = false)
    private LocalDate diaFinal;

    @OneToMany(mappedBy = "campana")
    private List<ParticipaEntity> participaciones = new ArrayList<>();
}
