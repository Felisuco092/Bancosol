/**
 * Clase de logica de negocio de las campañas.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 1%
 * - Félix Jiménez Almanza: 19%
 * - Jorge Torres Sánchez: 80%
 */
package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.dto.CampanaDTO;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.entity.ParticipaId;
import uma.grupo13.bancosol.mappers.CampanaMapper;
import uma.grupo13.bancosol.mappers.TiendaMapper;
import uma.grupo13.bancosol.services.utils.Roles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CampanasService {
    private final CampanaRepository campanaRepository;
    private final ParticipaRepository participaRepository;
    private final CadenaRepository cadenaRepository;
    private final CampanaMapper campanaMapper;
    private final TiendaMapper tiendaMapper;

    public List<CampanaDTO> listarCampanas() {
        List<CampanaEntity> lista= campanaRepository.findAll();
        return campanaMapper.toDTOList(lista);
    }

    public CampanaDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        CampanaEntity campana = campanaRepository.getReferenceById(id);
        return campanaMapper.toDTO(campana);
    }

    public void borrarCampana(Integer idCampana) {
        CampanaEntity campanaDelete = campanaRepository.getReferenceById(idCampana);
        campanaDelete.eliminarParticipaciones();
        campanaRepository.delete(campanaDelete);
    }

    public String guardarCampana(Integer idCampana, String nombre, LocalDate fechaInic, LocalDate fechaFin, List<Integer> idCadenas) {
        if(this.seSolapaCampanya(fechaInic,fechaFin,idCampana)){
            return "solapamiento";
        }
        if(this.fechaInvalida(fechaInic,fechaFin)){
            return "fecha_invalida";
        }

        CampanaEntity campana;
        boolean isNew = false;
        if(idCampana == null){
            campana= new CampanaEntity();
            isNew = true;
        }else{
            campana = this.campanaRepository.getReferenceById(idCampana);
        }

        campana.setNombre(nombre);
        campana.setAno(fechaInic.getYear());
        campana.setDiaComienzo(fechaInic);
        campana.setDiaFinal(fechaFin);

        CampanaEntity savedCampana = campanaRepository.save(campana);

        if (isNew && idCadenas != null && !idCadenas.isEmpty()) {
            for (Integer idCadena : idCadenas) {
                CadenaEntity cadena = cadenaRepository.findById(idCadena).orElse(null);
                if (cadena != null) {
                    for (TiendaEntity tienda : cadena.getTiendas()) {
                        ParticipaEntity participa = new ParticipaEntity();
                        ParticipaId participaId = new ParticipaId();
                        participaId.setIdCampana(savedCampana.getId());
                        participaId.setIdTienda(tienda.getId());
                        participa.setId(participaId);
                        participa.setCampana(savedCampana);
                        participa.setTienda(tienda);
                        participaRepository.save(participa);
                    }
                }
            }
        }

        return null;
    }

    public CampanaDTO findCampanaActiva() {
        List<CampanaEntity> campanasList = campanaRepository.findCampanaActiva();
        if (campanasList.isEmpty()) {
            return null;
        }
        CampanaEntity campana = campanasList.get(0);
        return campanaMapper.toDTO(campana);
    }

    public List<TiendaDTO> filtrarTiendasParticipaCampanaPorRol(Integer idCampana, UsuarioDTO usuario) {
        List<TiendaEntity> tiendas;
        Integer rolId = usuario.getRol().getId();
        Integer userId = usuario.getId();

        if (rolId == Roles.ADMIN || rolId == Roles.RESP_ENTIDAD) {
            tiendas = participaRepository.findTiendasByCampanaId(idCampana);
        } else if (rolId == Roles.COORDINADOR) {
            tiendas = participaRepository.findTiendasByCampanaAndCoord(idCampana, userId);
        } else if (rolId == Roles.CAPITAN) {
            tiendas = participaRepository.findTiendasByCampanaAndCapi(idCampana, userId);
        } else if (rolId == Roles.RESP_TIENDA) {
            tiendas = participaRepository.findTiendasByCampanaAndResponsable(idCampana, userId);
        } else {
            tiendas = new ArrayList<>();
        }
        return tiendaMapper.toDTOList(tiendas);
    }

    private boolean seSolapaCampanya(LocalDate fechaInic, LocalDate fechaFin, Integer idCampana) {
        List<CampanaDTO> campanas = this.listarCampanas();
        for (CampanaDTO campana : campanas) {
            if(fechaFin.isAfter(campana.getDiaComienzo()) &&  fechaInic.isBefore(campana.getDiaFinal()) && (idCampana==null || idCampana != campana.getId())) {
                return true;
            }
        }
        return false;
    }
    private boolean fechaInvalida(LocalDate fechaInic, LocalDate fechaFin) {
        if(fechaInic.isAfter(fechaFin)){
            return true;
        }else{
            return false;
        }
    }
}
