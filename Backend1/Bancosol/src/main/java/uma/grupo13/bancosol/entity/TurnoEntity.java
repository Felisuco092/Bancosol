package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "turno")
@Getter
@Setter
@NoArgsConstructor
public class TurnoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_turno", nullable = false)
    private String tipoTurno; // Viernes Mañana, Sábado Tarde, etc. [cite: 686, 687]

    private LocalDate dia;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_campana", nullable = false)
    private CampanaEntity campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_voluntario", nullable = false)
    private VoluntarioBaseEntity voluntario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda", nullable = false)
    private TiendaEntity tienda;

    /* No hace falta porque TurnoEntity no depende de TiendaEntity
    public void eliminarDatos(){
        this.campana = null;
        this.voluntario = null;
        this.tienda = null;
        this.dia = null;
    }*/
}
