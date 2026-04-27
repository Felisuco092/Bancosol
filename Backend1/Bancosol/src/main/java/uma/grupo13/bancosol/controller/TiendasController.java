package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tiendas")
public class TiendasController {
    @GetMapping("/")
    public String doTiendas(Model model) {
        model.addAttribute("paginaActual", "tiendas");
        return "tiendas";
    }

    @PostMapping("/editar")
    public  String doEditarTiendas(Model model) {
        return "";
    }

    @PostMapping("/crear")
    public  String doCrearTiendas(Model model) {
        return "";
    }

    @PostMapping("/borrar")
    public  String doBorrarTiendas(Model model) {
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarTiendas(Model model) {
        return "redirect:/tiendas";
    }
}
