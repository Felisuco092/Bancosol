package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/campanas")
public class CampanasController {
    @Autowired
    protected CampanaRepository campanaRepository;


    @GetMapping("/")
    public String doCampanas(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        List<CampanaEntity> campanas = campanaRepository.findAll();
        model.addAttribute("paginaActual", "campanas");
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @PostMapping("/editar")
    public  String doEditarCampana(@RequestParam(value = "idCampana", required = false) Integer idCampana,
            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CampanaEntity campanaEdit = this.campanaRepository.getReferenceById(idCampana);
        model.addAttribute("campana", campanaEdit);
        return "crear_editar/crear_editar_campana";
    }

    @GetMapping("/crear")
    public String doCrearCampana(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        return "crear_editar/crear_editar_campana";
    }

    @PostMapping("/borrar")
    public  String doBorrarCampana(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/guardar")
    public  String doGuardarCampana(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/campanas";
    }

}
