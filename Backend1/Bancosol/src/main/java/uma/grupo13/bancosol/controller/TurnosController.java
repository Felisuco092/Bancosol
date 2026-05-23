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
        model.addAttribute("turno", newTurno);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);

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
    public  String doGuardarTurnos(@RequestParam(value = "tipo-turno") String tipoTurno,
                                   @RequestParam(value = "dia") LocalDate fecha,
                                   @RequestParam(value = "hora-inicio") LocalTime horaInicio,
                                   @RequestParam(value = "hora-fin") LocalTime horaFin,
                                   @RequestParam(value = "idCampana") Integer idCampana,
                                   @RequestParam(value = "idTienda") Integer idTienda,
                                   Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        
        try {
            TurnoEntity newTurno = new TurnoEntity();
            newTurno.setTipoTurno(tipoTurno);
            newTurno.setDia(fecha);
            newTurno.setHoraInicio(horaInicio);
            newTurno.setHoraFin(horaFin);
            
            CampanaEntity campana = campanasService.buscarPorId(idCampana);
            TiendaEntity tienda = tiendasService.buscarPorId(idTienda);
            
            if (campana == null || tienda == null) {
                // Si falta alguno, redirigir con error o manejarlo
                return "redirect:/turnos/crear"; 
            }
            
            newTurno.setCampana(campana);
            newTurno.setTienda(tienda);
            
            turnosService.guardarTurno(newTurno);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/turnos/crear"; // O una página de error específica
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
