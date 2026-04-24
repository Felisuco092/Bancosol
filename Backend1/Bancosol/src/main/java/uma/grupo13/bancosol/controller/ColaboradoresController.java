package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    @GetMapping("/")
    public String doColaboradores(Model model) {
        model.addAttribute("paginaActual", "colaboradores");
        return "colaboradores";
    }
}
