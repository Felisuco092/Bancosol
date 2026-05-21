package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.dao.TiendasRepository;
import uma.grupo13.bancosol.dao.TurnoRepository;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/turnos")
public class TurnosController {
    @Autowired
    protected TurnoRepository turnoRepository;

    @Autowired
    protected CampanaRepository campanaRepository;

    @Autowired
    protected TiendasRepository tiendaRepository;

    @GetMapping("/")
    public String doTurnos(Model model, @RequestParam(name="campana", required = false)CampanaEntity campana, HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        List<TurnoEntity> turnos = turnoRepository.findAll();
        List<CampanaEntity> campanas = campanaRepository.findAll();
        List<TiendaEntity> tiendas = tiendaRepository.findAll();
        model.addAttribute("paginaActual", "turnos");
        model.addAttribute("turnos", turnos);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);

        return "turnos";
    }

    @GetMapping("/editar")
    public  String doEditarTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        return "crear_editar/crear_turno";
    }

    @GetMapping("/crear")
    public  String doCrearTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        TurnoEntity newTurno = new TurnoEntity();
        List<CampanaEntity> campanas = campanaRepository.findAll();
        List<TiendaEntity> tiendas = tiendaRepository.findAll();
        model.addAttribute("turno", newTurno);
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);

        return "crear_editar/crear_turno";
    }

    @PostMapping("/borrar")
    public  String doBorrarTurnos(@RequestParam(value="idTurno") Integer idTurno,
                                Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        TurnoEntity turnoDelete = turnoRepository.getReferenceById(idTurno);
        turnoDelete.eliminarDatos(); // eliminar lo datos restantes: campaña perteneciente, voluntarios, tiendas y día
        turnoRepository.delete(turnoDelete);

        return "redirect:/turnos/";
    }

    @PostMapping("/guardar")
    public  String doGuardarTurnos(@RequestParam(value = "tipo-turno") String tipoTurno,
                                   @RequestParam(value = "dia") LocalDate fecha,
                                   @RequestParam(value = "hora-inicio") LocalTime horaInicio,
                                   @RequestParam(value = "hora-fin") LocalTime horaFin,
                                   @RequestParam(value = "idCampana") Integer idCampana,
                                   @RequestParam(value = "idTienda") Integer idTienda,
                                   Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        
        try {
            TurnoEntity newTurno = new TurnoEntity();
            newTurno.setTipoTurno(tipoTurno);
            newTurno.setDia(fecha);
            newTurno.setHoraInicio(horaInicio);
            newTurno.setHoraFin(horaFin);
            
            CampanaEntity campana = campanaRepository.findById(idCampana).orElse(null);
            TiendaEntity tienda = tiendaRepository.findById(idTienda).orElse(null);
            
            if (campana == null || tienda == null) {
                // Si falta alguno, redirigir con error o manejarlo
                return "redirect:/turnos/crear"; 
            }
            
            newTurno.setCampana(campana);
            newTurno.setTienda(tienda);
            
            turnoRepository.save(newTurno);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/turnos/crear"; // O una página de error específica
        }

        return "redirect:/turnos/";
    }

    @PostMapping("/filtrar")
    public String doFiltrarTurnos(@RequestParam(value = "idCampana", required = false) Integer idCampana,
                                  @RequestParam(value = "idTienda", required = false) Integer idTienda,
                                  Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        if(idCampana != null && idTienda != null){
            List<TurnoEntity> turnosFiltrados = turnoRepository.filtrarTurnos(idCampana, idTienda);
            model.addAttribute("turnos", turnosFiltrados);
            
            TiendaEntity tienda = tiendaRepository.findById(idTienda).orElse(null);
            if (tienda != null && tienda.getCapitan() != null) {
                model.addAttribute("capitanNombre", tienda.getCapitan().getNombre() + " " + tienda.getCapitan().getApellidos());
            }
        }else{// Sin filtro aplicado: devolver todos los turnos
            List<TurnoEntity> turnos = turnoRepository.findAll();
            model.addAttribute("turnos", turnos);

        }
        List<CampanaEntity> campanas = campanaRepository.findAll();
        List<TiendaEntity> tiendas = tiendaRepository.findAll();
        model.addAttribute("paginaActual", "turnos");
        model.addAttribute("campanas", campanas);
        model.addAttribute("tiendas", tiendas);
        model.addAttribute("idCampanaSel", idCampana);
        model.addAttribute("idTiendaSel", idTienda);

        return "turnos";
    }

    @PostMapping("/incidencia")
    public String doIncidencia(@RequestParam(value="idTurno") Integer idTurno,
            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        model.addAttribute("idTurno", idTurno);

        return "crear_editar/incidencia";
    }

    @PostMapping("/anadir")
    public String doAnadirVoluntario(HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/eliminar")
    public String doEliminarVoluntario(HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

}
