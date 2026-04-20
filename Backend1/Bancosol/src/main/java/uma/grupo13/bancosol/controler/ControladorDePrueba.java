package uma.grupo13.bancosol.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ControladorDePrueba {
    @GetMapping("/prueba")
    public String doStart() {
        return "bandeja";
    }


}
