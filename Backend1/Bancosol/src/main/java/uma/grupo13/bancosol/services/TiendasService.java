package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.entity.TiendaEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class TiendasService {
    private final TiendasRepository tiendasRepo;

    public List<TiendaEntity> listarTiendas(){
        return tiendasRepo.findAll();
    }

    public TiendaEntity buscarPorId(Integer id) {
        return tiendasRepo.findById(id).orElse(null);
    }

    public TiendaEntity getReferenceById(Integer id) {
        return tiendasRepo.getReferenceById(id);
    }

    public List<TiendaEntity> filtroLocalidad(String local) {
        return tiendasRepo.filtroLocalidad(local);
    }

    public List<TiendaEntity> filtroLocalidadCadena(String local, Integer idCad) {
        return tiendasRepo.filtroLocalidadCadena(local, idCad);
    }

    public void borrarTiendaPorId(Integer id) {
        tiendasRepo.deleteById(id);
    }

    public TiendaEntity guardarTienda(TiendaEntity tienda) {
        return tiendasRepo.save(tienda);
    }

}
