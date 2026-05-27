package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.dto.VoluntarioDTO;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import uma.grupo13.bancosol.mappers.VoluntarioMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class VoluntariosService {
    private final VoluntariosRepository voluntariosRepository;
    private final UserRepository userRepository;
    private final VoluntarioMapper voluntarioMapper;

    public List<VoluntarioDTO> listarVoluntarios() {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findAll();
        return voluntarioMapper.toDTOList(lista);
    }

    public List<String> findLocalidadesDistintas() {
        return voluntariosRepository.findLocalidadesDistintas();
    }

    public List<VoluntarioDTO> findAllByLocalidad(String localidad) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findAllByLocalidad(localidad);
        return voluntarioMapper.toDTOList(lista);
    }

    public List<VoluntarioDTO> findBaseFisicos(String localidad) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findBaseFisicos(localidad);
        return voluntarioMapper.toDTOList(lista);
    }

    public List<VoluntarioDTO> findBaseEntidades(String localidad) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findBaseEntidades(localidad);
        return voluntarioMapper.toDTOList(lista);
    }

    public List<VoluntarioDTO> findPendientes(String localidad) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findPendientes(localidad);
        return voluntarioMapper.toDTOList(lista);
    }

    public VoluntarioDTO buscarPorId(Integer id) {
        if (id==null) {return null;}
        VoluntarioBaseEntity voluntario = voluntariosRepository.findById(id).orElse(null);
        return voluntarioMapper.toDTO(voluntario);
    }

    public List<VoluntarioDTO> findAllByIds(List<Integer> ids) {
        if(ids==null){return null;}
        List<VoluntarioBaseEntity> listaVolEntity = voluntariosRepository.findAllById(ids);
        return voluntarioMapper.toDTOList(listaVolEntity);
    }

    public void deleteById(Integer id) {
        voluntariosRepository.deleteById(id);
    }


    public void guardarVoluntario(Integer id, String tipo, String domicilio, String zonaGeografica, String codigoPostal,
                                   String nombre, String apellidos, String nombreAsociacion,
                                  Integer nVoluntarios, Boolean confirmar, Integer idResponsableEntidad) {

        VoluntarioBaseEntity voluntario;

        if (id != null) {
            voluntario = voluntariosRepository.getReferenceById(id);
        } else {
            if ("fisico".equals(tipo)) {
                voluntario = new VoluntarioFisicoEntity();
            } else {
                voluntario = new VoluntarioEntidadEntity();
            }
        }

        if (confirmar == null) { confirmar = false; }

        voluntario.setAprobado(confirmar);
        voluntario.setDomicilio(domicilio);
        voluntario.setZonaGeografica(zonaGeografica);
        voluntario.setCodigoPostal(codigoPostal);


        if (voluntario instanceof VoluntarioFisicoEntity) {
            VoluntarioFisicoEntity fisico = (VoluntarioFisicoEntity) voluntario;
            fisico.setNombre(nombre);
            fisico.setApellidos(apellidos);
        } else if (voluntario instanceof VoluntarioEntidadEntity) {
            VoluntarioEntidadEntity entidad = (VoluntarioEntidadEntity) voluntario;
            entidad.setNombreAsociacion(nombreAsociacion);
            entidad.setNVoluntarios(nVoluntarios);
            if (idResponsableEntidad != null) {
                UsuarioEntity responsable = userRepository.getReferenceById(idResponsableEntidad);
                entidad.setResponsableEntidad(responsable);
            } else {
                entidad.setResponsableEntidad(null);
            }
        }

        voluntariosRepository.save(voluntario);
    }

    public int countTotalPersonasVoluntarias() {
        return voluntariosRepository.countTotalPersonasVoluntarias();
    }
}
