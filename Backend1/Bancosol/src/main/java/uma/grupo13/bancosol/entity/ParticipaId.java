package uma.grupo13.bancosol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ParticipaId implements Serializable {
    @Column(name = "id_campana")
    private Integer idCampana;

    @Column(name = "id_tienda")
    private Integer idTienda;
}
