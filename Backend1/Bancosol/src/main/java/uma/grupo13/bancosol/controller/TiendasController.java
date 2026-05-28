package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.*;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.services.*;
import uma.grupo13.bancosol.services.utils.Permiso;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.util.ArrayList;
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
    private final ValidaSesion validaSesion;



    @GetMapping("/")
    public String doTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.TIENDAS)) return "redirect:/dashboard";
        model.addAttribute("paginaActual", "tiendas");
        List<TiendaDTO> tiendas= new ArrayList<>();
        if(user.getRol().getId()==1){
            tiendas = tiendasService.listarTiendas();
        }else if (user.getRol().getId()==2){
            tiendas = tiendasService.listarTiendasCoord(user.getId());
        }else if (user.getRol().getId()==3){
            tiendas = tiendasService.listarTiendasCapi(user.getId());
        }


        List<CampanaDTO> campanas = campanasService.listarCampanas();
        List<CadenaDTO> cadenas = cadenaService.listarCadenas();
        
        Set<String> localidades = tiendasService.getLocalidades();
                
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
    public String doEditarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_TIENDA)) return "redirect:/dashboard";
        TiendaDTO tienda = tiendasService.buscarPorId(id);
        model.addAttribute("tienda", tienda);
        List<CadenaDTO> cadenas = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenas);
        List<CampanaDTO> campanas= campanasService.listarCampanas();
        model.addAttribute("campanas", campanas);
        List<UsuarioDTO> capitanes=usuariosService.findCapitanes();
        model.addAttribute("capitanes", capitanes);
        List<UsuarioDTO> responsablesTienda = usuariosService.findResponsablesTienda();
        model.addAttribute("responsablesTienda", responsablesTienda);
        return "crear_editar/crear_editar_tienda";
    }

    @GetMapping("/crear")
    public String doCrearTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_TIENDA)) return "redirect:/dashboard";

        model.addAttribute("tienda", new TiendaDTO());
        List<CadenaDTO> cadenas = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenas);
        List<CampanaDTO> campanas= campanasService.listarCampanas();
        model.addAttribute("campanas", campanas);
        List<UsuarioDTO> capitanes=usuariosService.findCapitanes();
        model.addAttribute("capitanes", capitanes);
        List<UsuarioDTO> responsablesTienda = usuariosService.findResponsablesTienda();
        model.addAttribute("responsablesTienda", responsablesTienda);
        return "crear_editar/crear_editar_tienda";
    }


    @PostMapping("/filtrar")
    public String doFiltro(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                           @RequestParam("idCadena") Integer idCad,
                           @RequestParam("idCampana") Integer idCamp,
                           @RequestParam("localidad") String localidad) {
        if (user == null) return "redirect:/";


        List<TiendaDTO> tiendas= new ArrayList<>();

        tiendas = tiendasService.filtrarTiendasDependiendoDelRol(user, idCad, localidad, tiendas);


        model.addAttribute("tiendas", tiendas);
        model.addAttribute("idCampanaActual", idCamp);
        return "tablas/tiendas";
    }



    @PostMapping("/borrar")
    public String doBorrarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_TIENDA)) return "redirect:/dashboard";
        tiendasService.borrarTiendaPorId(id);
        return "redirect:/tiendas/";
    }

    @PostMapping("/guardar")
    public String doGuardarTiendas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                                   @RequestParam(value = "id", required = false) Integer id,
                                   @RequestParam("descripcion") String descripcion,
                                   @RequestParam("localidad") String localidad,
                                   @RequestParam("domicilio") String domicilio,
                                   @RequestParam("cPostal") String cPostal,
                                   @RequestParam("zonaGeografica") String zonaGeografica,
                                   @RequestParam("cadena") Integer idCadena,
                                   @RequestParam(value = "capitan", required = false) Integer idCapitan,
                                   @RequestParam(value = "responsableTienda", required = false) Integer idResponsableTienda,
                                   @RequestParam(value = "campanasParticipa", required = false) List<Integer> idCampanas) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_TIENDA)) return "redirect:/dashboard";

        TiendaDTO tienda;
        tienda=tiendasService.guardarTienda(id, descripcion, localidad, domicilio, cPostal, zonaGeografica, idCadena, idCapitan, idResponsableTienda);

        // Actualizar participaciones
        if (id != null) {
            // Borrar participaciones anteriores si es edición
            List<ParticipaDTO> actuales = participaService.findByIdTienda(id);
            participaService.deleteAll(actuales);
        }

        if (idCampanas != null) {
            participaService.guardarParticipaciones(idCampanas, tienda.getId());
        }

        return "redirect:/tiendas/";
    }
}
