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
import uma.grupo13.bancosol.entity.CampanaEntity;

import java.util.List;

@Controller
public class ControllerBase {
    @Autowired
    protected CampanaRepository campanaRepository;


    @GetMapping("/")
    public String doStart(){
        return "index";
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model) {
        model.addAttribute("paginaActual", "dashboard");
        return "dashboard";
    }

    @PostMapping("/login")
    public String dologin(@RequestParam("username") String user,
                          @RequestParam("password") String password,
                          HttpSession session) {
        if ("admin".equals(user) && "paswword123".equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            return "redirect:/"; // de momento devuelve a la página de inicio
        }
    }

    @PostMapping("/logout")
    public String dologout(){
        return "redirect:/";
    }

}

// Post para login, logaut-> ver en movies
// recargar tablas
// post de cada boton: redirije a /get con los datos necesarios, ya esta el de bandeja