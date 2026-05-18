package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/tiendas")
public class TiendasController {
    @GetMapping("/")
    public String doTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "tiendas");
        return "tiendas";
    }




    @PostMapping("/editar")
    public  String doEditarTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @GetMapping("/crear")
    public  String doCrearTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "crear_editar/crear_editar_tienda";
    }

    @PostMapping("/borrar")
    public  String doBorrarTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarTiendas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/tiendas";
    }
}
