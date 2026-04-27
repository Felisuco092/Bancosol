package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    @GetMapping("/")
    public String doColaboradores(Model model) {
        model.addAttribute("paginaActual", "colaboradores");
        return "colaboradores";
    }

    @PostMapping("/editar")
    public  String doEditarColaboradores(Model model) {
        return "";
    }

    @PostMapping("/crear")
    public  String doCrearColaboradores(Model model) {
        return "";
    }

    @PostMapping("/borrar")
    public  String doBorrarColaboradores(Model model) {
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarColaboradores(Model model) {
        return "redirect:/colaboradores";
    }
}
