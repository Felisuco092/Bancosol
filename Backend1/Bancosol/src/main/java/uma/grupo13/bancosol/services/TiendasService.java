package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.mappers.TiendaMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TiendasService {
    private final TiendasRepository tiendasRepo;
    private final CadenaRepository cadenaRepo;
    private final UserRepository userRepo;
    private final TiendaMapper tiendaMapper;

    public List<TiendaDTO> listarTiendas(){
        List<TiendaEntity> lista = tiendasRepo.findAll();
        return tiendaMapper.toDTOList(lista);
    }

    public TiendaDTO buscarPorId(Integer id) {
        TiendaEntity tienda = tiendasRepo.findById(id).orElse(null);
        return tiendaMapper.toDTO(tienda);
    }

    public TiendaDTO getReferenceById(Integer id) {
        if (id==null) {return null;}
        TiendaEntity tienda = tiendasRepo.getReferenceById(id);
        return tiendaMapper.toDTO(tienda);
    }

    public List<TiendaDTO> filtroLocalidad(String local) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidad(local);
        return tiendaMapper.toDTOList(lista);
    }

    public List<TiendaDTO> filtroLocalidadCadena(String local, Integer idCad) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidadCadena(local, idCad);
        return tiendaMapper.toDTOList(lista);
    }

    public void borrarTiendaPorId(Integer id) {
        TiendaEntity tienda = tiendasRepo.getReferenceById(id);
        tienda.getParticipaciones().clear();
        tienda.getTurnos().clear();
        tiendasRepo.deleteById(id);
    }

    public TiendaDTO guardarTienda(Integer id, String descripcion, String localidad, String domicilio, String cPostal, String zonaGeografica, Integer idCadena, Integer idCapitan) {
        TiendaEntity tienda;
        if (id != null) {
            tienda = tiendasRepo.getReferenceById(id);
            if (tienda == null) tienda = new TiendaEntity();
        } else {
            tienda = new TiendaEntity();
        }

        tienda.setDescripcion(descripcion);
        tienda.setLocalidad(localidad);
        tienda.setDomicilio(domicilio);
        tienda.setCPostal(cPostal);
        tienda.setZonaGeografica(zonaGeografica);

        CadenaEntity cadena = cadenaRepo.getReferenceById(idCadena);
        tienda.setCadena(cadena);

        UsuarioEntity capitan = userRepo.getReferenceById(idCapitan);
        tienda.setCapitan(capitan);

        tienda = tiendasRepo.save(tienda);
        return tiendaMapper.toDTO(tienda);
    }

    public Set<String> getLocalidades() {
        return tiendasRepo.getLocalidades();
    }

    public List<TiendaDTO> listarTiendasCoord(Integer id){
        List<TiendaEntity> lista = tiendasRepo.getAllCoord(id);
        return tiendaMapper.toDTOList(lista);
    }
    public List<TiendaDTO> listarTiendasCapi(Integer id){
        List<TiendaEntity> lista = tiendasRepo.getAllCapi(id);
        return tiendaMapper.toDTOList(lista);
    }

    public List<TiendaDTO> filtroLocalidadCoord(String local, Integer idCoord) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidadCoord(local, idCoord);
        return tiendaMapper.toDTOList(lista);
    }

    public List<TiendaDTO> filtroLocalidadCadenaCoord(String local, Integer idCad, Integer idCoord) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidadCadenaCoord(local, idCad, idCoord);
        return tiendaMapper.toDTOList(lista);
    }

    public List<TiendaDTO> filtroLocalidadCapi(String local, Integer idCapi) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidadCapi(local, idCapi);
        return tiendaMapper.toDTOList(lista);
    }

    public List<TiendaDTO> filtroLocalidadCadenaCapi(String local, Integer idCad, Integer idCapi) {
        List<TiendaEntity> lista = tiendasRepo.filtroLocalidadCadenaCapi(local, idCad, idCapi);
        return tiendaMapper.toDTOList(lista);
    }
}
