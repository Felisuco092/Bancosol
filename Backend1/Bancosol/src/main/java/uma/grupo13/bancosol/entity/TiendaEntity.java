package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tienda")
@Getter
@Setter
@NoArgsConstructor
public class TiendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descripcion;
    private String localidad;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    @Column(name = "c_postal")
    private String cPostal;

    @Column(name = "zona_geografica")
    private String zonaGeografica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cadena", nullable = false)
    private CadenaEntity cadena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_capitan")
    private UsuarioEntity capitan; // Relación con Usuario (Rol Capitán)

    @OneToMany(mappedBy = "tienda")
    private List<ParticipaEntity> participaciones = new ArrayList<>();
}
