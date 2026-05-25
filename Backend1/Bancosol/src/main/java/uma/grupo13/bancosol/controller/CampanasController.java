package uma.grupo13.bancosol.controller;

import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.CampanaDTO;
import uma.grupo13.bancosol.dto.TurnoDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.CampanasService;
import uma.grupo13.bancosol.services.TurnosService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/campanas")
public class CampanasController {
    @Autowired
    protected CampanasService campanasService;

    @Autowired
    protected TurnosService turnosService;


    @GetMapping("/")
    public String doCampanas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";

        List<CampanaDTO> campanas = campanasService.listarCampanas();
        model.addAttribute("paginaActual", "campanas");
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @GetMapping("/editar")
    public  String doEditarCampana(@RequestParam("id") Integer idCampana,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        CampanaDTO campanaEdit = campanasService.getReferenceById(idCampana);
        model.addAttribute("campana", campanaEdit);
        return "crear_editar/crear_editar_campana";
    }

    @GetMapping("/crear")
    public String doCrearCampana(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        model.addAttribute("campana", new CampanaDTO());
        return "crear_editar/crear_editar_campana";
    }

    @PostMapping("/borrar")
    public  String doBorrarCampana(@RequestParam("idCampana") Integer idCampana,
                                   Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";


        List<TurnoDTO> turnos = turnosService.filtrarTurnos(idCampana, null);
        turnosService.deleteAll(turnos);
        campanasService.borrarCampana(idCampana);
        return "redirect:/campanas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCampana(@RequestParam(value ="idCampana", required = false) Integer idCampana,
                                    @RequestParam(value="nombre", required = false) String nombre,
                                    @RequestParam(value="anyo", required = false) Integer anyo,
                                    @RequestParam(value="fecha-inicio", required = false) LocalDate fechaInic,
                                    @RequestParam(value="fecha-fin", required = false) LocalDate fechaFin,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";

        campanasService.guardarCampana(idCampana, nombre, anyo, fechaInic, fechaFin);

        return "redirect:/campanas/";
    }

}
