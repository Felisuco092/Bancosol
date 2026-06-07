package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.dto.VoluntarioDTO;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import uma.grupo13.bancosol.mappers.VoluntarioMapper;
import uma.grupo13.bancosol.services.utils.Roles;

import org.hibernate.Hibernate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VoluntariosService {
    private final VoluntariosRepository voluntariosRepository;
    private final UserRepository userRepository;
    private final VoluntarioMapper voluntarioMapper;

    public List<VoluntarioDTO> listarVoluntarios() {
        List<VoluntarioBaseEntity> listaVoluntarios = voluntariosRepository.findAll();
        return voluntarioMapper.toDTOList(listaVoluntarios);
    }

    public List<VoluntarioDTO> listarVoluntariosResponsable(Integer id) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findAllResponsable(id);
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

    public List<VoluntarioDTO> findAllByLocalidadResponsable(String localidad, Integer id) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findAllByLocalidadResponsable(localidad, id);
        return voluntarioMapper.toDTOList(lista);
    }

    public List<VoluntarioDTO> findPendientesResponsable(String localidad, Integer id) {
        List<VoluntarioBaseEntity> lista= voluntariosRepository.findPendientesResponsable(localidad, id);
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

        Object actualEntity = Hibernate.unproxy(voluntario); // error corregido con IA generativa
        if (actualEntity instanceof VoluntarioFisicoEntity) {
            VoluntarioFisicoEntity fisico = (VoluntarioFisicoEntity) actualEntity;
            fisico.setNombre(nombre);
            fisico.setApellidos(apellidos);
        } else if (actualEntity instanceof VoluntarioEntidadEntity) {
            VoluntarioEntidadEntity entidad = (VoluntarioEntidadEntity) actualEntity;
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

    public List<VoluntarioDTO> listarVoluntariosSegunRol(UsuarioDTO user) {
        Integer rolId = user.getRol().getId();
        if (rolId == Roles.ADMIN || rolId == Roles.COORDINADOR || rolId == Roles.CAPITAN) {
            return listarVoluntarios();
        } else if (rolId == Roles.RESP_ENTIDAD) {
            return listarVoluntariosResponsable(user.getId());
        }
        return new ArrayList<>();
    }

    public List<VoluntarioDTO> filtrarColaboradores(UsuarioDTO user, String tipo, String localidad) {
        String localidadParam = (localidad == null || localidad.equals("all")) ? "" : localidad;
        Integer rolId = user.getRol().getId();
        Integer userId = user.getId();

        if (rolId == Roles.ADMIN || rolId == Roles.COORDINADOR || rolId == Roles.CAPITAN) {
            if (tipo == null || tipo.equals("all")) {
                return findAllByLocalidad(localidadParam);
            } else if (tipo.equals("true")) {
                return findBaseFisicos(localidadParam);
            } else if (tipo.equals("false")) {
                return findBaseEntidades(localidadParam);
            } else {
                return findPendientes(localidadParam);
            }
        } else if (rolId == Roles.RESP_ENTIDAD) {
            if (tipo == null || tipo.equals("all") || tipo.equals("false")) {
                return findAllByLocalidadResponsable(localidadParam, userId);
            } else if (tipo.equals("true")) {
                return new ArrayList<>();
            } else {
                return findPendientesResponsable(localidadParam, userId);
            }
        }
        return new ArrayList<>();
    }
}
