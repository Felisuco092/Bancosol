package uma.grupo13.bancosol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @GetMapping("/")
    public String doUsuarios(Model model){
        model.addAttribute("paginaActual", "usuarios");
        return "usuarios";
    }

}
