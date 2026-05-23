package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.entity.CadenaEntity;

import java.util.List;

@Service
@AllArgsConstructor
public class CadenaService {
    private final CadenaRepository cadenaRepository;

    public List<CadenaEntity> listarCadenas() {
        return cadenaRepository.findAll();
    }

    public CadenaEntity buscarPorId(Integer id) {
        return cadenaRepository.findById(id).orElse(null);
    }

    public CadenaEntity getReferenceById(Integer id) {
        return cadenaRepository.getReferenceById(id);
    }

    public void borrarCadena(CadenaEntity cadena) {
        cadenaRepository.delete(cadena);
    }

    public void guardarCadena(CadenaEntity cadena) {
        cadenaRepository.save(cadena);
    }

    public List<CadenaEntity> cadenasPorTiendas() {
        return cadenaRepository.cadenasPorTiendas();
    }
}
