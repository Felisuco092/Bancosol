package uma.grupo13.bancosol.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.*;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.services.CampanasService;
import uma.grupo13.bancosol.services.TiendasService;
import uma.grupo13.bancosol.services.TurnosService;
import uma.grupo13.bancosol.services.VoluntariosService;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/turnos")
public class TurnosController {
    private final TurnosService turnosService;
    private final CampanasService campanasService;
    private final TiendasService tiendasService;
    private final VoluntariosService voluntariosService;
    private final ValidaSesion validaSesion;

    @GetMapping("/")
    public String doTurnos(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user){
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "turnos")) return "redirect:/dashboard";
        List<TurnoDTO> turnos = turnosService.listarTurnos();
        List<CampanaDTO> campanas = campanasService.listarCampanas();
        List<TiendaDTO> tiendas= new ArrayList<>();
        if(user.getRol().getId()==1){
            tiendas = tiendasService.listarTiendas();
        }else if (user.getRol().getId()==2){
            tiendas = tiendasService.listarTiendasCoord(user.getId());
        }else if (user.getRol().getId()==3){
            tiendas = tiendasService.listarTiendasCapi(user.getId());
        }
        model.addAttribute("paginaActual", "turnos");
        model.addAttribute("turnos", turnos);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);

        return "turnos";
    }

    @GetMapping("/editar")
    public  String doEditarTurnos(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "editarTurnos")) return "redirect:/dashboard";
        return "crear_editar/crear_turno";
    }

    @GetMapping("/crear")
    public  String doCrearTurnos(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "editarTurnos")) return "redirect:/dashboard";
        TurnoDTO newTurno = new TurnoDTO();
        List<CampanaDTO> campanas = campanasService.listarCampanas();
        List<TiendaDTO> tiendas = tiendasService.listarTiendas();
        List<VoluntarioDTO> voluntarios = voluntariosService.listarVoluntarios();
        model.addAttribute("turno", newTurno);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);
        model.addAttribute("voluntarios", voluntarios);

        return "crear_editar/crear_turno";
    }

    @PostMapping("/borrar")
    public  String doBorrarTurnos(@RequestParam(value="idTurno") Integer idTurno,
                                Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "editarTurnos")) return "redirect:/dashboard";
        //turnoDelete.eliminarDatos(); // eliminar lo datos restantes: campaña perteneciente, voluntarios, tiendas y día
        turnosService.borrarTurnoId(idTurno);

        return "redirect:/turnos/";
    }

    @PostMapping("/guardar")
    public String doGuardarTurnos(@RequestParam(value = "tipo-turno") String tipoTurno,
                                  @RequestParam(value = "dia") String fechaStr,
                                  @RequestParam(value = "hora-inicio") String horaInicioStr,
                                  @RequestParam(value = "hora-fin") String horaFinStr,
                                  @RequestParam(value = "idCampana") Integer idCampana,
                                  @RequestParam(value = "idTienda") Integer idTienda,
                                  @RequestParam(value = "idVoluntario") Integer idVoluntario,
                                  Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "editarTurnos")) return "redirect:/dashboard";
        try {
            turnosService.guardarTurno(tipoTurno, fechaStr, horaInicioStr, horaFinStr, idCampana, idTienda, idVoluntario);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar el turno: " + e.getMessage());
            model.addAttribute("tipoTurno", tipoTurno);
            model.addAttribute("dia", fechaStr);
            model.addAttribute("horaInicio", horaInicioStr);
            model.addAttribute("horaFin", horaFinStr);
            model.addAttribute("idCampanaSel", idCampana);
            model.addAttribute("idTiendaSel", idTienda);
            model.addAttribute("idVoluntarioSel", idVoluntario);

            List<CampanaDTO> campanas = campanasService.listarCampanas();
            List<TiendaDTO> tiendas = tiendasService.listarTiendas();
            List<VoluntarioDTO> voluntarios = voluntariosService.listarVoluntarios();
            model.addAttribute("voluntarios", voluntarios);
            model.addAttribute("campanas", campanas);
            model.addAttribute("tiendas", tiendas);
            
            return "crear_editar/crear_turno";
        }

        return "redirect:/turnos/";
    }

    @PostMapping("/filtrar")
    public String doFiltrarTurnos(@RequestParam(value = "idCampana", required = false) Integer idCampana,
                                  @RequestParam(value = "idTienda", required = false) Integer idTienda,
                                  Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "turnos")) return "redirect:/dashboard";
        List<TurnoDTO> turnosFiltrados = turnosService.filtrarTurnos(idCampana, idTienda);
        model.addAttribute("turnos", turnosFiltrados);

        if (idTienda != null) {
            TiendaDTO tienda = tiendasService.buscarPorId(idTienda);
            if (tienda != null && tienda.getCapitan() != null) {
                model.addAttribute("capitanNombre", tienda.getCapitan().getNombre() + " " + tienda.getCapitan().getApellidos());
            }
        }

        return "tablas/turnos";
    }

    @PostMapping("/incidencia")
    public String doIncidencia(@RequestParam(value="idTurno") Integer idTurno,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), "incidencias")) return "redirect:/dashboard";
        model.addAttribute("idTurno", idTurno);

        return "crear_editar/incidencia";
    }

    @GetMapping("/tiendas-por-campana")
    @ResponseBody
    // Para este hemos recurrido a la IA generativa
    public List<Map<String, Object>> doTiendasPorCampana(@RequestParam("idCampana") Integer idCampana,
                                                         @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return List.of();

        List<TiendaDTO> tiendas = campanasService.filtrarTiendasParticipaCampana(idCampana);
        return tiendas.stream().map(t -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", t.getId());
            m.put("descripcion", t.getDescripcion());
            return m;
        }).collect(Collectors.toList());
    }

}