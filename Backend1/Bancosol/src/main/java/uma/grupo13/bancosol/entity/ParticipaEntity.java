/**
 * Clase que representa una participacion de una tienda en una campaña dentro del sistema.
 *
 * Autores:
 * - IA Generativa: 100%
 */
package uma.grupo13.bancosol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participa")
@Getter
@Setter
@NoArgsConstructor
public class ParticipaEntity {
    @EmbeddedId
    private ParticipaId id = new ParticipaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idCampana")
    @JoinColumn(name = "id_campana")
    private CampanaEntity campana;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTienda")
    @JoinColumn(name = "id_tienda")
    private TiendaEntity tienda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coordinador")
    private UsuarioEntity coordinador; // [cite: 736]
}
