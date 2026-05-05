package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.utils.ValidaSesion;

@Controller
@RequestMapping("/cadenas")
public class CadenaController {
    @Autowired
    protected CadenaRepository  cadenaRepository;

    @GetMapping("/")
    public String doCadena(HttpSession session, Model model) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";


        model.addAttribute("paginaActual", "cadenas");
        return "cadenas";
    }
}
