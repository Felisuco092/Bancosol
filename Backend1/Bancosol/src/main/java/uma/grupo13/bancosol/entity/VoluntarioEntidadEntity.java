package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Voluntario_Entidad")
@PrimaryKeyJoinColumn(name = "id_voluntario")
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioEntidadEntity extends VoluntarioBaseEntity {

    @Column(columnDefinition = "TEXT")
    private String nombreAsociacion;

    private Integer nVoluntarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_responsable", nullable = false)
    private UsuarioEntity responsable;
}
