package uma.grupo13.bancosol.mappers;
//Gemini nos ha ayudado con la parte de Hibernate.unproxy ya que eso no lo hemos dado en la asignatura, para poder convertirlo a 
//VoluntarioFisicoEntity o VoluntarioEntidadEntity

import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.VoluntarioDTO;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import org.hibernate.Hibernate;

@Component
public class VoluntarioMapper extends MapperDTO<VoluntarioDTO, VoluntarioBaseEntity> {
    @Override
    public VoluntarioDTO toDTO(VoluntarioBaseEntity entity) {
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
            dto.setNombreDisplay(entidad.getNombreAsociacion());
            dto.setNumeroVoluntariosDisplay(entidad.getNVoluntarios());
        }

        return dto;
    }
}
