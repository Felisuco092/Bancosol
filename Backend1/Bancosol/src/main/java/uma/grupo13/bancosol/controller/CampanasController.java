/**
 * Clase que representa los endpoints de las campañas.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 20%
 * - Félix Jiménez Almanza: 25%
 * - Jorge Torres Sánchez: 55%
 */
package uma.grupo13.bancosol.controller;

import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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
import uma.grupo13.bancosol.services.CadenaService;
import uma.grupo13.bancosol.services.utils.Permiso;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/campanas")
public class CampanasController {
    private final CampanasService campanasService;
    private final TurnosService turnosService;
    private final CadenaService cadenaService;
    private final ValidaSesion validaSesion;


    @GetMapping("/")
    public String doCampanas(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CAMPANAS)) return "redirect:/dashboard";

        List<CampanaDTO> campanas = campanasService.listarCampanas();
        model.addAttribute("paginaActual", "campanas");
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @GetMapping("/editar")
    public  String doEditarCampana(@RequestParam("id") Integer idCampana,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CAMPANAS)) return "redirect:/dashboard";
        CampanaDTO campanaEdit = campanasService.getReferenceById(idCampana);
        model.addAttribute("campana", campanaEdit);
        return "crear_editar/crear_editar_campana";
    }

    @GetMapping("/crear")
    public String doCrearCampana(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CAMPANAS)) return "redirect:/dashboard";
        model.addAttribute("campana", new CampanaDTO());
        model.addAttribute("cadenas", cadenaService.listarCadenas());

        return "crear_editar/crear_editar_campana";
    }

    @PostMapping("/borrar")
    public  String doBorrarCampana(@RequestParam("idCampana") Integer idCampana,
                                   Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CAMPANAS)) return "redirect:/dashboard";

        List<TurnoDTO> turnos = turnosService.filtrarTurnos(idCampana, null);
        turnosService.deleteAll(turnos);
        campanasService.borrarCampana(idCampana);
        return "redirect:/campanas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCampana(@RequestParam(value ="idCampana", required = false) Integer idCampana,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("fecha-inicio") LocalDate fechaInic,
                                    @RequestParam("fecha-fin") LocalDate fechaFin,
                                    @RequestParam(value = "cadenasParticipantes", required = false) List<Integer> idCadenas,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CAMPANAS)) return "redirect:/dashboard";

        String error = campanasService.guardarCampana(idCampana, nombre, fechaInic, fechaFin, idCadenas);
        if (error != null) {
            CampanaDTO campana;
            if (idCampana != null) {
                campana = campanasService.getReferenceById(idCampana);
            } else {
                campana = new CampanaDTO();
                campana.setNombre(nombre);
                campana.setDiaComienzo(fechaInic);
                campana.setDiaFinal(fechaFin);
                model.addAttribute("cadenas", cadenaService.listarCadenas());
            }
            String msg = "solapamiento".equals(error)
                    ? "La campaña se solapa con otra existente"
                    : "La fecha de inicio debe ser anterior a la fecha de fin";
            model.addAttribute("campana", campana);
            model.addAttribute("error", msg);
            return "crear_editar/crear_editar_campana";
        }

        return "redirect:/campanas/";
    }

}
