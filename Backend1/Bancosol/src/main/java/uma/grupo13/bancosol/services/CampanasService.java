package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.dto.CampanaDTO;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.mappers.CampanaMapper;
import uma.grupo13.bancosol.mappers.TiendaMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CampanasService {
    private final CampanaRepository campanaRepository;
    private final ParticipaRepository participaRepository;
    private final CampanaMapper campanaMapper;
    private final TiendaMapper tiendaMapper;

    public List<CampanaDTO> listarCampanas() {
        List<CampanaEntity> lista= campanaRepository.findAll();
        return campanaMapper.toDTOList(lista);
    }

    public CampanaDTO buscarPorId(Integer id) {
        if (id==null) {return null;}
        CampanaEntity campana = campanaRepository.findById(id).orElse(null);
        return campanaMapper.toDTO(campana);
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

    public boolean guardarCampana(Integer idCampana, String nombre, Integer anyo, LocalDate fechaInic, LocalDate fechaFin) {
        CampanaEntity campana;
        if(this.seSolapaCampanya(fechaInic,fechaFin)){
            return false;
        }
        if(idCampana == null){
            campana= new CampanaEntity();
        }else{
            campana = this.campanaRepository.getReferenceById(idCampana);
        }

        campana.setNombre(nombre);
        campana.setAno(anyo);
        campana.setDiaComienzo(fechaInic);
        campana.setDiaFinal(fechaFin);

        campanaRepository.save(campana);
        return true;
    }

    public CampanaDTO findCampanaActiva() {
        CampanaEntity campana = campanaRepository.findCampanaActiva(LocalDate.now());
        return campanaMapper.toDTO(campana);
    }

    public List<TiendaDTO> filtrarTiendasParticipaCampana(Integer idCampana) {
        List<TiendaEntity> tiendas = participaRepository.findTiendasByCampanaId(idCampana);
        return tiendaMapper.toDTOList(tiendas);
    }

    private boolean seSolapaCampanya(LocalDate fechaInic, LocalDate fechaFin) {
        List<CampanaDTO> campanas = this.listarCampanas();
        for (CampanaDTO campana : campanas) {
            if(fechaFin.isAfter(campana.getDiaComienzo()) &&  fechaInic.isBefore(campana.getDiaFinal())) {
                return true;
            }
        }
        return false;
    }
}
