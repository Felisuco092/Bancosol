package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.services.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tiendas")
@AllArgsConstructor
public class TiendasController {
    private final TiendasService tiendasService;
    private final CampanasService campanasService;
    private final CadenaService cadenaService;
    private final ParticipaService participaService;
    private final UsuariosService usuariosService;


    @GetMapping("/")
    public String doTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        model.addAttribute("paginaActual", "tiendas");
        List<TiendaEntity> tiendas = tiendasService.listarTiendas();
        List<CampanaEntity> campanas = campanasService.listarCampanas();
        List<CadenaEntity> cadenas = cadenaService.listarCadenas();
        
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
    public String doEditarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        TiendaEntity tienda = tiendasService.buscarPorId(id);
        model.addAttribute("tienda", tienda);
        List<CadenaEntity> cadenas = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenas);
        List<CampanaEntity> campanas= campanasService.listarCampanas();
        model.addAttribute("campanas", campanas);
        List<UsuarioEntity> capitanes=usuariosService.findCapitanes();
        model.addAttribute("capitanes", capitanes);
        return "crear_editar/crear_editar_tienda";
    }

    @GetMapping("/crear")
    public String doCrearTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        model.addAttribute("tienda", new TiendaEntity());
        List<CadenaEntity> cadenas = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenas);
        List<CampanaEntity> campanas= campanasService.listarCampanas();
        model.addAttribute("campanas", campanas);
        List<UsuarioEntity> capitanes=usuariosService.findCapitanes();
        model.addAttribute("capitanes", capitanes);
        return "crear_editar/crear_editar_tienda";
    }


    @PostMapping("/filtrar")
    public String doFiltro(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user, 
                           @RequestParam("idCadena") Integer idCad,
                           @RequestParam("idCampana") Integer idCamp,
                           @RequestParam("localidad") String localidad) {
        if (user == null) return "redirect:/";
        
        List<TiendaEntity> tiendas;

        if (idCad!=0) {
            tiendas = tiendasService.filtroLocalidadCadena(localidad, idCad);
        } else{
            tiendas = tiendasService.filtroLocalidad(localidad);
        }

        model.addAttribute("tiendas", tiendas);
        model.addAttribute("idCampanaActual", idCamp);
        return "tablas/tiendas";
    }

    @PostMapping("/borrar")
    public String doBorrarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        TiendaEntity tienda=tiendasService.getReferenceById(id);
        List<ParticipaEntity> participaciones=tienda.getParticipaciones();
        participaService.deleteAll(participaciones);
        tiendasService.borrarTiendaPorId(id);
        return "redirect:/tiendas/";
    }

    @PostMapping("/guardar")
    public String doGuardarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user,
                                   @RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam("descripcion") String descripcion,
                                   @RequestParam("localidad") String localidad,
                                   @RequestParam("domicilio") String domicilio,
                                   @RequestParam("cPostal") String cPostal,
                                   @RequestParam("zonaGeografica") String zonaGeografica,
                                   @RequestParam("cadena") Integer idCadena,
                                   @RequestParam(value = "capitan", required = false) Integer idCapitan,
                                   @RequestParam(value = "campanasParticipa", required = false) List<Integer> idCampanas) {
        if (user == null) return "redirect:/";

        TiendaEntity tienda;
        if (id != null) {
            tienda = tiendasService.buscarPorId(id);
            if (tienda == null) tienda = new TiendaEntity();
        } else {
            tienda = new TiendaEntity();
        }

        tienda.setDescripcion(descripcion);
        tienda.setLocalidad(localidad);
        tienda.setDomicilio(domicilio);
        tienda.setCPostal(cPostal);
        tienda.setZonaGeografica(zonaGeografica);

        CadenaEntity cadena = cadenaService.buscarPorId(idCadena);
        tienda.setCadena(cadena);

        UsuarioEntity capitan = usuariosService.buscarPorId(idCapitan);
        tienda.setCapitan(capitan);

        tienda = tiendasService.guardarTienda(tienda);

        // Actualizar participaciones
        if (id != null) {
            // Borrar participaciones anteriores si es edición
            List<ParticipaEntity> actuales = participaService.findByIdTienda(id);
            participaService.deleteAll(actuales);
        }

        if (idCampanas != null) {
            for (Integer idCampana : idCampanas) {
                ParticipaEntity participa = new ParticipaEntity();
                participa.getId().setIdTienda(tienda.getId());
                participa.getId().setIdCampana(idCampana);
                
                CampanaEntity campana = campanasService.buscarPorId(idCampana);
                participa.setCampana(campana);
                participa.setTienda(tienda);
                
                participaService.guardarParticipacion(participa);
            }
        }

        return "redirect:/tiendas/";
    }
}
