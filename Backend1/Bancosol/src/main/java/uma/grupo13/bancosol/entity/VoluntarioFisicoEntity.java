package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Voluntario_Fisico")
@PrimaryKeyJoinColumn(name = "id_voluntario")
@Getter
@Setter
@NoArgsConstructor
public class VoluntarioFisicoEntity extends VoluntarioBaseEntity {

    @Column(columnDefinition = "TEXT")
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String apellidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordinador", nullable = false)
    private UsuarioEntity coordinador;
}
