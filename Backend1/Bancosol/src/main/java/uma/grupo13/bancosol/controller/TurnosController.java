package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TiendaEntity;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/turnos")
public class TurnosController {
    @GetMapping("/")
    public String doTurnos(Model model, @RequestParam(name="campana", required = false)CampanaEntity campana, HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "turnos");
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

        return "crear_editar/crear_editar_turno";
    }

    @PostMapping("/borrar")
    public  String doBorrarTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/turnos/";
    }

    @PostMapping("/filtrar")
    public String doFiltrarTurnos(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
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
