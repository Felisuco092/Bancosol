package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import uma.grupo13.bancosol.dao.*;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;
import java.util.Optional;

@Controller
public class ControllerBase {
    @Autowired
    protected UserRepository userRepo;
    @Autowired
    protected TiendasRepository tiendasRepo;
    @Autowired
    protected VoluntariosRepository voluntariosRepo;
    @Autowired
    protected CampanaRepository campanaRepo;
    @Autowired
    protected CadenaRepository cadenaRepo;


    @GetMapping("/")
    public String doStart(@SessionAttribute(name = "user", required = false) UsuarioEntity user){
        if (user != null) {
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user != null) {
            model.addAttribute("paginaActual", "dashboard");
            List<TiendaEntity> tiendas = tiendasRepo.findAll();
            model.addAttribute("tiendas", tiendas);
            
            int totalVoluntarios = voluntariosRepo.countTotalPersonasVoluntarias();
            model.addAttribute("totalVoluntarios", totalVoluntarios);

            Optional<CampanaEntity> campanaOpt = campanaRepo.findCampanaActiva();
            CampanaEntity campana = campanaOpt.orElse(null);
            model.addAttribute("campana", campana);
            List<CadenaEntity> cadenas = cadenaRepo.cadenasPorTiendas().subList(0, Math.min(5, cadenaRepo.findAll().size()));
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
        UsuarioEntity usuario = userRepo.autheticate(user, pass);

        if (usuario != null) {
            session.setAttribute("user", usuario);
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
