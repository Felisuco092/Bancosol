package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/bandeja")
public class BandejaController {
    @GetMapping("/")
    public String doBandeja(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "bandeja");
        return "bandeja";
    }

    @PostMapping("/mensaje")
    public String doMensaje(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

}
