package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tiendas")
public class TiendasController {
    @GetMapping("/")
    public String doTiendas(Model model) {
        model.addAttribute("paginaActual", "tiendas");
        return "tiendas";
    }
}
