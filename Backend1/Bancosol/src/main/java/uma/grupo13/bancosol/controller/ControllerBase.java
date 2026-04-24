package uma.grupo13.bancosol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/bandeja")
    public String doBandeja(Model model) {
        model.addAttribute("paginaActual", "bandeja");
        return "bandeja";
    }

    @GetMapping("/dashboard")
    public String doDashboard(Model model) {
        model.addAttribute("paginaActual", "dashboard");
        return "dashboard";
    }

}

//Post para login, logaut y guardar/edit/crear
    /*
    public String doLogin(@RequestParam("username") String user,
                          @RequestParam("password") String password,
                          HttpSession session) {
        if("admin".equals(user) && "paswword123".equals(password)){
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        }else{
            return "redirect:/"; // de momento devuelve a la página de inicio
        }

    }*/

//login a /db, logout a / y guardar a /tipo redirect