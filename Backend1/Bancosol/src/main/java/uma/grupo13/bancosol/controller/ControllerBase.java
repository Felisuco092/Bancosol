/**
 * Clase que representa los endpoints del dashboard y del login.
 *
 * Autores:
 * - Germán Pelaez Gallardo: 15%
 * - Jorge Torres Sánchez: 85%
 */
package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import uma.grupo13.bancosol.dto.CadenaDTO;
import uma.grupo13.bancosol.dto.CampanaDTO;
import uma.grupo13.bancosol.dto.TiendaDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.services.*;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor

public class ControllerBase {
    private final UsuariosService usuariosService;
    private final TiendasService tiendasServ;
    private final VoluntariosService voluntariosService;
    private final CampanasService campanasService;
    private final CadenaService cadenaService;
    private final ValidaSesion validaSesion;


    @GetMapping("/")
    public String doStart(@SessionAttribute(name = "user", required = false) UsuarioDTO user){
        if (user != null) {
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user != null) {
            model.addAttribute("paginaActual", "dashboard");
            List<TiendaDTO> tiendas = tiendasServ.listarTiendas();
            model.addAttribute("tiendas", tiendas);
            
            int totalVoluntarios = voluntariosService.countTotalPersonasVoluntarias();
            model.addAttribute("totalVoluntarios", totalVoluntarios);

            CampanaDTO campana = campanasService.findCampanaActiva();
            model.addAttribute("campana", campana);
            List<CadenaDTO> cadenas = cadenaService.cadenasPorTiendas();
            if (cadenas.size() > 5) cadenas = cadenas.subList(0, 5);
            model.addAttribute("cadenas", cadenas);

            model.addAttribute("user", user);
            return "dashboard";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String dologin(@RequestParam("username") String user, @RequestParam("password") String pass,
                          HttpSession session, Model model) {
        UsuarioDTO usuario = usuariosService.autheticate(user, pass);

        if (usuario != null) {
            session.setAttribute("user", usuario);
            session.setAttribute("permisos", validaSesion.getPermisos(usuario.getRol().getId()));
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Usuario no encontrado o error de autenticación");
            return "index";
        }
    }

    @PostMapping("/logout")
    public String dologout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
