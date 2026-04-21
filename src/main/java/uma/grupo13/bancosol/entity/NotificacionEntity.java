package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICACION")
@Getter
@Setter
@NoArgsConstructor
public class NotificacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_destino", nullable = false)
    private UsuarioEntity usuarioDestino; // [cite: 750]

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
}
