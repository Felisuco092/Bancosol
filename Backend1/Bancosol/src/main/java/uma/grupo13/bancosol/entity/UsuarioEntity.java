package uma.grupo13.bancosol.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    private String apellidos;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefono;

    @Column(name = "area_asignada")
    private String areaAsignada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    @OneToMany(mappedBy = "usuarioDestino")
    private List<NotificacionEntity> notificaciones = new ArrayList<>();

    @OneToMany(mappedBy = "capitan")
    private List<TiendaEntity> tiendasComoCapitan = new ArrayList<>();
}
