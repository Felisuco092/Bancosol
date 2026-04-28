package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.entity.UsuarioEntity;

import java.util.List;

@Controller
public class ControllerBase {
    @Autowired
    protected UserRepository userRepo;


    @GetMapping("/")
    public String doStart(HttpSession session){
        if (verificarSesion(session)) {
            return "redirect:/dashboard";
        }else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model, HttpSession session) {
        if (verificarSesion(session)) {
            model.addAttribute("paginaActual", "dashboard");
            return "dashboard";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/login")
    public String dologin(@RequestParam("username") String user, @RequestParam("password") String password,
                          HttpSession session, Model model) {
        if ("admin".equals(user) && "paswword123".equals(password)) {
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

    protected boolean verificarSesion(HttpSession session){
        return session.getAttribute("user") != null;
    }
}
// datos en la bd
// recargar tablas
// post de cada boton: redirije a /get con los datos necesarios, ya esta el de bandeja