package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bandeja")
public class BandejaController {
    @GetMapping("/")
    public String doBandeja(Model model) {
        model.addAttribute("paginaActual", "bandeja");
        return "bandeja";
    }

    @PostMapping("/mensaje")
    public String doMensaje(Model model) {
        return "";
    }

}
