package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.entity.CampanaEntity;

import java.util.List;

@Controller
public class controler1 {
    @Autowired
    protected CampanaRepository campanaRepository;


    @GetMapping("/")
    public String doLogin(@RequestParam("username") String user,
                          @RequestParam("password") String password,
                          HttpSession session) {
        if("admin".equals(user) && "paswword123".equals(password)){
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }else{
            return "redirect:/"; // de momento devolver a la página de inicio
        }

    }

    @GetMapping("/bandeja")
    public String doStart(Model model) {
        model.addAttribute("paginaActual", "bandeja");
        return "bandeja";
    }

    @GetMapping("/campanas")
    public String doCampanas(Model model) {
        List<CampanaEntity> campanas = campanaRepository.findAll();
        model.addAttribute("paginaActual", "campanas");
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @GetMapping("/colaboradores")
    public String doColaboradores(Model model) {
        model.addAttribute("paginaActual", "colaboradores");
        return "colaboradores";
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model) {
        model.addAttribute("paginaActual", "dashboard");
        return "dashboard";
    }

    @GetMapping("/tiendas")
    public String doTiendas(Model model) {
        model.addAttribute("paginaActual", "tiendas");
        return "tiendas";
    }

    @GetMapping("/turnos")
    public String doTurnos(Model model){
        model.addAttribute("paginaActual", "turnos");
        return "turnos";
    }

    @GetMapping("/usuarios")
    public String doUsuarios(Model model){
        model.addAttribute("paginaActual", "usuarios");
        return "usuarios";
    }

}
