package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    @GetMapping("/")
    public String doColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "colaboradores");
        return "colaboradores";
    }

    @PostMapping("/editar")
    public  String doEditarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/crear")
    public  String doCrearColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/borrar")
    public  String doBorrarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/colaboradores";
    }
}
