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

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
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

    @PostMapping("/editar")
    public  String doEditarTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        return "crear_editar/crear_editar_turno";
    }

    @PostMapping("/crear")
    public  String doCrearTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        TurnoEntity newTurno = new TurnoEntity();
        model.addAttribute("turno", newTurno);

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
                                   @RequestParam(value = "dia") Date fecha,
                                   @RequestParam(value = "hora-inicio") LocalTime horaInicio,
                                   @RequestParam(value = "hora-fin") LocalTime horaFin,
                                   Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        TurnoEntity newTurno = new TurnoEntity();
        newTurno.setTipoTurno(tipoTurno);
        newTurno.setHoraFin(horaInicio);
        newTurno.setHoraFin(horaFin);
        turnoRepository.save(newTurno);

        return "redirect:/turnos/";
    }

    @PostMapping("/filtrar")
    public String doFiltrarTurnos(@RequestParam(value = "") Integer idCampana,
                                  @RequestParam(value = "") Integer idTienda,
                                  Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

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
