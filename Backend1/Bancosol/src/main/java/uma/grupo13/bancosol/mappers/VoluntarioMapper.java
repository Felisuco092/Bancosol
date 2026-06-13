package uma.grupo13.bancosol.mappers;
/**
 * Clase que representa el mapper de los voluntarios.
 *
 * Autores:
 * -IA Generativa: 20%
 * Aclaración: La IA Generativa nos ha ayudado en el uso de Hibernate.unproxy ya que eso no entra en los contenidos la asignatura.
 * En esta clase mapper se controla si el voluntario que se pasa por argumento es de tipo VoluntarioFisicoEntity o VoluntarioEntidadEntity,
 * y, en función del tipo del que sea, se establecerán unos u otros atributos.
 */
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.VoluntarioDTO;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import org.hibernate.Hibernate;

@Component
@AllArgsConstructor
public class VoluntarioMapper extends MapperDTO<VoluntarioDTO, VoluntarioBaseEntity> {
    private final UsuarioMapper usuarioMapper;
    @Override
    public VoluntarioDTO toDTO(VoluntarioBaseEntity entity) {
        if (entity == null) return null;

        VoluntarioDTO dto = new VoluntarioDTO();
        dto.setId(entity.getId());
        dto.setDomicilio(entity.getDomicilio());
        dto.setZonaGeografica(entity.getZonaGeografica());
        dto.setCodigoPostal(entity.getCodigoPostal());
        dto.setAprobado(entity.getAprobado());

        Object actualEntity = Hibernate.unproxy(entity);

        if (actualEntity instanceof VoluntarioFisicoEntity) {
            VoluntarioFisicoEntity fisico = (VoluntarioFisicoEntity) actualEntity;
            dto.setTipo("FISICO");
            dto.setNombre(fisico.getNombre());
            dto.setApellidos(fisico.getApellidos());
            dto.setNombreDisplay(fisico.getNombre() + " " + fisico.getApellidos());
            dto.setNumeroVoluntariosDisplay(1);
        } else if (actualEntity instanceof VoluntarioEntidadEntity) {
            VoluntarioEntidadEntity entidad = (VoluntarioEntidadEntity) actualEntity;
            dto.setTipo("ENTIDAD");
            dto.setNombreAsociacion(entidad.getNombreAsociacion());
            dto.setNVoluntarios(entidad.getNVoluntarios());
            dto.setResponsableEntidad(usuarioMapper.toDTO(entidad.getResponsableEntidad()));
            dto.setNombreDisplay(entidad.getNombreAsociacion());
            dto.setNumeroVoluntariosDisplay(entidad.getNVoluntarios());
        }

        return dto;
    }
}
