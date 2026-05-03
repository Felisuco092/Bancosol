package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String doStart(HttpSession session){
        if (ValidaSesion.verificarSesion(session)) {
            return "redirect:/dashboard";
        }else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model, HttpSession session) {
        if (ValidaSesion.verificarSesion(session)) {
            model.addAttribute("paginaActual", "dashboard");
            List<TiendaEntity> tiendas = tiendasRepo.findAll();
            model.addAttribute("tiendas", tiendas);
            List<VoluntarioEntity> voluntarios = voluntariosRepo.findAll();
            model.addAttribute("voluntarios", voluntarios);
            Optional<CampanaEntity> campanaOpt = campanaRepo.findCampanaActiva();
            CampanaEntity campana = campanaOpt.orElse(null);
            model.addAttribute("campana", campana);
            List<CadenaEntity> cadenas = cadenaRepo.cadenasPorTiendas().subList(0, Math.min(5, cadenaRepo.findAll().size()));
            model.addAttribute("cadenas", cadenas);
            return "dashboard";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String dologin(@RequestParam("username") String user, @RequestParam("password") String pass,
                          HttpSession session, Model model) {
        if ("admin".equals(user) && "admin".equals(pass)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            return "redirect:/"; // de momento devuelve a la página de inicio
        }
        /*UserEditorEntity editor = userEditorRepository.autheticate(username, password);
        if (editor == null) {
            model.addAttribute("error", "Usuario no encontrado o error de autenticación");
            return "index";
        } else {
            session.setAttribute("user", editor);
            session.setAttribute("rol", editor.getRol())
            return "redirect:/dashboard";
        }*/
    }

    @PostMapping("/logout")
    public String dologout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
