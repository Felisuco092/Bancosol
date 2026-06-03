package uma.grupo13.bancosol.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.ParticipaRepository;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.ParticipaDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.entity.ParticipaId;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.mappers.ParticipaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipaService {
    private final ParticipaRepository participaRepository;
    private final ParticipaMapper participaMapper;
    private final TiendasRepository tiendasRepository;
    private final CampanaRepository campanaRepository;
    private final UserRepository userRepository;

    public List<ParticipaDTO> findByIdTienda(Integer id) {
        List<ParticipaEntity> lista= participaRepository.findByIdTienda(id);
        return participaMapper.toDTOList(lista);
    }

    public List<ParticipaDTO> findByCoordinadorId(Integer id) {
        List<ParticipaEntity> lista= participaRepository.findByCoordinadorId(id);
        return participaMapper.toDTOList(lista);
    }

    public void deleteAll(List<ParticipaDTO> participaciones) {
        for(ParticipaDTO p: participaciones){
            ParticipaId participaId = new ParticipaId();
            participaId.setIdCampana(p.getIdCampana());
            participaId.setIdTienda(p.getIdTienda());
            participaRepository.deleteById(participaId);
        }
    }

    public void guardarParticipacion(Integer idCampana, Integer idTienda) {
        TiendaEntity tienda = tiendasRepository.getReferenceById(idTienda);
        CampanaEntity campana = campanaRepository.getReferenceById(idCampana);

        ParticipaId participaId = new ParticipaId();
        participaId.setIdCampana(idCampana);
        participaId.setIdTienda(idTienda);

        ParticipaEntity participa = new ParticipaEntity();
        participa.setId(participaId);
        participa.setCampana(campana);
        participa.setTienda(tienda);

        participaRepository.save(participa);
    }

    public void guardarParticipaciones(List<Integer> idCampanas, Integer idTienda, HttpServletRequest request) {
        TiendaEntity tienda = tiendasRepository.getReferenceById(idTienda);

        for (Integer idCampana : idCampanas){
            ParticipaEntity participa = new ParticipaEntity();
            participa.getId().setIdTienda(idTienda);
            participa.getId().setIdCampana(idCampana);

            CampanaEntity campana = campanaRepository.getReferenceById(idCampana);
            participa.setCampana(campana);
            participa.setTienda(tienda);

            String coordParam = request.getParameter("coordinador_" + idCampana);
            if (coordParam != null && !coordParam.isEmpty()) {
                participa.setCoordinador(userRepository.getReferenceById(Integer.parseInt(coordParam)));
            }

            participaRepository.save(participa);
        }
    }

    public void editarParticipaCoordinador(Integer id){
        List<ParticipaEntity> participacionesCoord= participaRepository.findByCoordinadorId(id);
        for (ParticipaEntity p : participacionesCoord) {
            p.setCoordinador(null);
        }
        participaRepository.saveAll(participacionesCoord);
    }
}
