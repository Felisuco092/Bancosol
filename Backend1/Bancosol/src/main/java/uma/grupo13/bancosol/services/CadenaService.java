package uma.grupo13.bancosol.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.dto.CadenaDTO;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.mappers.CadenaMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class CadenaService {
    private final CadenaRepository cadenaRepository;
    private final CadenaMapper cadenaMapper;


    public List<CadenaDTO> listarCadenas() {
        List<CadenaEntity> lista= cadenaRepository.findAll();
        return cadenaMapper.toDTOList(lista);
    }

    public CadenaDTO buscarPorId(Integer id) {
        CadenaEntity cadena = cadenaRepository.findById(id).orElse(null);
        return cadenaMapper.toDTO(cadena);
    }

    public CadenaDTO getReferenceById(Integer id) {
        CadenaEntity cadena = cadenaRepository.getReferenceById(id);
        return cadenaMapper.toDTO(cadena);
    }

    public void borrarCadenaId(Integer id) {
        CadenaEntity cadena = cadenaRepository.getReferenceById(id);
        cadena.eliminarTiendas();
        cadenaRepository.delete(cadena);
    }

    public void guardarCadena(Integer id, String nombre, String codigo) {
        CadenaEntity cadena;
        if (id == null) {
            cadena = new CadenaEntity();
        } else {
            cadena = cadenaRepository.getReferenceById(id);
        }

        cadena.setNombre(nombre);
        cadena.setCodigo(codigo);
        cadenaRepository.save(cadena);
    }

    public List<CadenaDTO> cadenasPorTiendas() {
        List<CadenaEntity> lista= cadenaRepository.cadenasPorTiendas();
        return cadenaMapper.toDTOList(lista);
    }
}
