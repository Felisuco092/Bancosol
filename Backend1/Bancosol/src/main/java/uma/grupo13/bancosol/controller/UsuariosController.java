package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.UserRepository;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    protected UserRepository userRepo;

    @GetMapping("/")
    public String doUsuarios(Model model, HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        model.addAttribute("paginaActual", "usuarios");
        List<UsuarioEntity> usuarios= userRepo.findAll();
        model.addAttribute("users", usuarios);
        return "usuarios";
    }

    @PostMapping("/editar")
    public  String doEditarUsuarios(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/usuarios/";
    }

    @PostMapping("/crear")
    public  String doCrearUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/usuarios/";
    }

    @PostMapping("/borrar")
    public  String doBorrarUsuarios(Model model, HttpSession session, @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        return "redirect:/usuarios/";
    }

    @PostMapping("/guardar")
    public  String doGuardarUsuarios(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/usuarios/";
    }
}
