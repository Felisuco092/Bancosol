package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.mappers.TiendaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class TiendasService {
    private final TiendasRepository tiendasRepo;
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
        tiendasRepo.deleteById(id);
    }

    public TiendaEntity guardarTienda(TiendaEntity tienda) {
        return tiendasRepo.save(tienda);
    }

}
