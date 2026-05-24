package uma.grupo13.bancosol.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.UsuarioEntity;

@Component
@AllArgsConstructor
public class UsuarioMapper extends MapperDTO<UsuarioDTO, UsuarioEntity> {
    private final RolMapper rolMapper;

    @Override
    public UsuarioDTO toDTO(UsuarioEntity entity) {
        if (entity == null) return null;
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellidos(entity.getApellidos());
        dto.setUsuario(entity.getUsuario());
        dto.setEmail(entity.getEmail());
        dto.setTelefono(entity.getTelefono());
        dto.setAreaAsignada(entity.getAreaAsignada());
        dto.setRol(rolMapper.toDTO(entity.getRol()));
        return dto;
    }
}
