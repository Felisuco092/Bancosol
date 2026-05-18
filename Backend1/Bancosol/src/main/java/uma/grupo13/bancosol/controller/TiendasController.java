package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tiendas")
@AllArgsConstructor
public class TiendasController {
    private final TiendasRepository tiendasRepo;
    private final CampanaRepository campanaRepo;
    private final CadenaRepository cadenaRepo;

    @GetMapping("/")
    public String doTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        model.addAttribute("paginaActual", "tiendas");
        List<TiendaEntity> tiendas = tiendasRepo.findAll();
        List<CampanaEntity> campanas = campanaRepo.findAll();
        List<CadenaEntity> cadenas = cadenaRepo.findAll();
        
        Set<String> localidades = tiendas.stream()
                .map(TiendaEntity::getLocalidad)
                .filter(loc -> loc != null && !loc.isEmpty())
                .collect(Collectors.toSet());
                
        model.addAttribute("tiendas", tiendas);
        model.addAttribute("campanas", campanas);
        model.addAttribute("cadenas", cadenas);
        model.addAttribute("localidades", localidades);

        if (!campanas.isEmpty()) {
            model.addAttribute("idCampanaActual", campanas.get(0).getId());
        }

        return "tiendas";
    }

    @GetMapping("/editar")
    public String doEditarTiendas(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        TiendaEntity tienda = tiendasRepo.findById(id).orElse(null);
        model.addAttribute("tienda", tienda);
        List<CadenaEntity> cadenas = cadenaRepo.findAll();
        model.addAttribute("cadenas", cadenas);
        return "crear_editar/crear_tienda";
    }

    @GetMapping("/crear")
    public String doCrearTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        model.addAttribute("tienda", new TiendaEntity());
        List<CadenaEntity> cadenas = cadenaRepo.findAll();
        model.addAttribute("cadenas", cadenas);
        return "crear_editar/crear_tienda";
    }


    @PostMapping("/filtrar")
    public String doFiltro(Model model, HttpSession session, 
                           @RequestParam("idCadena") Integer idCad,
                           @RequestParam("idCampana") Integer idCamp,
                           @RequestParam("localidad") String localidad) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        
        List<TiendaEntity> tiendas;

        if (idCad!=0) {
            tiendas = tiendasRepo.filtroLocalidadCadena(localidad, idCad);
        } else{
            tiendas = tiendasRepo.filtroLocalidad(localidad);
        }

        model.addAttribute("tiendas", tiendas);
        model.addAttribute("idCampanaActual", idCamp);
        return "tablas/tiendas";
    }

    @PostMapping("/borrar")
    public String doBorrarTiendas(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        tiendasRepo.deleteById(id);
        return "redirect:/tiendas/";
    }

    @PostMapping("/guardar")
    public String doGuardarTiendas(Model model, HttpSession session,
                                   @RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam("descripcion") String descripcion,
                                   @RequestParam("localidad") String localidad,
                                   @RequestParam("domicilio") String domicilio,
                                   @RequestParam("cPostal") String cPostal,
                                   @RequestParam("zonaGeografica") String zonaGeografica,
                                   @RequestParam("cadena") Integer idCadena) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        TiendaEntity tienda;
        if (id != null) {
            tienda = tiendasRepo.findById(id).orElse(new TiendaEntity());
        } else {
            tienda = new TiendaEntity();
        }

        tienda.setDescripcion(descripcion);
        tienda.setLocalidad(localidad);
        tienda.setDomicilio(domicilio);
        tienda.setCPostal(cPostal);
        tienda.setZonaGeografica(zonaGeografica);

        CadenaEntity cadena = cadenaRepo.findById(idCadena).orElse(null);
        tienda.setCadena(cadena);

        tiendasRepo.save(tienda);

        return "redirect:/tiendas/";
    }
}
