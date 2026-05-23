package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.CampanasService;
import uma.grupo13.bancosol.services.TiendasService;
import uma.grupo13.bancosol.services.TurnosService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/turnos")
public class TurnosController {
    private final TurnosService turnosService;
    private final CampanasService campanasService;
    private final TiendasService tiendasService;
    private final VoluntariosService voluntariosService;

    @Autowired
    protected VoluntariosRepository voluntariosRepository;

    @GetMapping("/")
    public String doTurnos(Model model, @RequestParam(name="campana", required = false)CampanaEntity campana, @SessionAttribute(name = "user", required = false) UsuarioEntity user){
        if (user == null) return "redirect:/";

        List<TurnoEntity> turnos = turnosService.listarTurnos();
        List<CampanaEntity> campanas = campanasService.listarCampanas();
        List<TiendaEntity> tiendas = tiendasService.listarTiendas();
        model.addAttribute("paginaActual", "turnos");
        model.addAttribute("turnos", turnos);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);

        return "turnos";
    }

    @GetMapping("/editar")
    public  String doEditarTurnos(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        return "crear_editar/crear_turno";
    }

    @GetMapping("/crear")
    public  String doCrearTurnos(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        TurnoEntity newTurno = new TurnoEntity();
        List<CampanaEntity> campanas = campanasService.listarCampanas();
        List<TiendaEntity> tiendas = tiendasService.listarTiendas();
        //List<VoluntarioBaseEntity> voluntarios = voluntariosRepository.findAll();  mirar
        model.addAttribute("turno", newTurno);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);
        model.addAttribute("voluntarios", voluntarios);

        return "crear_editar/crear_turno";
    }

    @PostMapping("/borrar")
    public  String doBorrarTurnos(@RequestParam(value="idTurno") Integer idTurno,
                                Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        TurnoEntity turnoDelete = turnosService.getReferenceById(idTurno);
        //turnoDelete.eliminarDatos(); // eliminar lo datos restantes: campaña perteneciente, voluntarios, tiendas y día
        turnosService.borrarTurno(turnoDelete);

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
                                  Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        try {
            TurnoEntity newTurno = new TurnoEntity();
            newTurno.setTipoTurno(tipoTurno);
            
            CampanaEntity campana = campanasService.buscarPorId(idCampana);
            TiendaEntity tienda = tiendasService.buscarPorId(idTienda);
            
            if (campana == null || tienda == null) {
                // Si falta alguno, redirigir con error o manejarlo
                return "redirect:/turnos/crear"; 
              
              //mirar
            if (fechaStr != null && !fechaStr.isEmpty()) {
                newTurno.setDia(LocalDate.parse(fechaStr));
            }
            if (horaInicioStr != null && !horaInicioStr.isEmpty()) {
                newTurno.setHoraInicio(LocalTime.parse(horaInicioStr));
            }
            if (horaFinStr != null && !horaFinStr.isEmpty()) {
                newTurno.setHoraFin(LocalTime.parse(horaFinStr));
            }

            CampanaEntity campana = campanaRepository.findById(idCampana).orElse(null);
            TiendaEntity tienda = tiendaRepository.findById(idTienda).orElse(null);
            VoluntarioBaseEntity voluntario = voluntariosRepository.findById(idVoluntario).orElse(null);

            if (campana == null || tienda == null || voluntario == null) {
                throw new Exception("Campaña o Tienda no encontrada");
            }

            newTurno.setCampana(campana);
            newTurno.setTienda(tienda);
            
            turnosService.guardarTurno(newTurno);
  //mira
            newTurno.setVoluntario(voluntario);

            turnoRepository.save(newTurno);
              
              //mira
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

            List<CampanaEntity> campanas = campanaRepository.findAll();
            List<TiendaEntity> tiendas = tiendaRepository.findAll();
            List<VoluntarioBaseEntity> voluntarios = voluntariosRepository.findAll();
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
                                  Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        List<TurnoEntity> turnosFiltrados = turnosService.filtrarTurnos(idCampana, idTienda);
        model.addAttribute("turnos", turnosFiltrados);

        if (idTienda != null) {
            TiendaEntity tienda = tiendasService.buscarPorId(idTienda);
            if (tienda != null && tienda.getCapitan() != null) {
                model.addAttribute("capitanNombre", tienda.getCapitan().getNombre() + " " + tienda.getCapitan().getApellidos());
            }
        }

        return "tablas/turnos";
    }

    @PostMapping("/incidencia")
    public String doIncidencia(@RequestParam(value="idTurno") Integer idTurno,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        model.addAttribute("idTurno", idTurno);

        return "crear_editar/incidencia";
    }

    @PostMapping("/anadir")
    public String doAnadirVoluntario(@SessionAttribute(name = "user", required = false) UsuarioEntity user){
        if (user == null) return "redirect:/";
        return "";
    }

    @PostMapping("/eliminar")
    public String doEliminarVoluntario(@SessionAttribute(name = "user", required = false) UsuarioEntity user){
        if (user == null) return "redirect:/";
        return "";
    }

}
