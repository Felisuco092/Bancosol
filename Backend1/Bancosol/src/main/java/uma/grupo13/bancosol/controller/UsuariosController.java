package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @GetMapping("/")
    public String doUsuarios(Model model, HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "usuarios");
        return "usuarios";
    }

    @PostMapping("/editar")
    public  String doEditarUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/crear")
    public  String doCrearUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/borrar")
    public  String doBorrarUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/usuarios";
    }
}
