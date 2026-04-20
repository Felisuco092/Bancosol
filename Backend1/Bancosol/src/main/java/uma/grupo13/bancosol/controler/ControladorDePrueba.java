package uma.grupo13.bancosol.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorDePrueba {
    @GetMapping("/bandeja")
    public String doStart() {
        return "bandeja";
    }
    @GetMapping("/campanas")
    public String doCampanas() {
        return "campanas";
    }
    @GetMapping("/colaboradores")
    public String doColaboradores() {
        return "colaboradores";
    }
    @GetMapping("/dashboard")
    public String doDashboard() {
        return "dashboard";
    }


}
