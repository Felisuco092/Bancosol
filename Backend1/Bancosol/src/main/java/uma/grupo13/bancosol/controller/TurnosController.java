package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/turnos")
public class TurnosController {
    @GetMapping("/")
    public String doTurnos(Model model){
        model.addAttribute("paginaActual", "turnos");
        return "turnos";
    }
}
