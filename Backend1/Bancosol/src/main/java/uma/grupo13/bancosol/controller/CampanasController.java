package uma.grupo13.bancosol.controller;

import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.CampanaEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.CampanasService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/campanas")
public class CampanasController {
    @Autowired
    protected CampanasService campanasService;


    @GetMapping("/")
    public String doCampanas(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        List<CampanaEntity> campanas = campanasService.listarCampanas();
        model.addAttribute("paginaActual", "campanas");
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @GetMapping("/editar")
    public  String doEditarCampana(@RequestParam("id") Integer idCampana,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        CampanaEntity campanaEdit = campanasService.getReferenceById(idCampana);
        model.addAttribute("campana", campanaEdit);
        return "crear_editar/crear_editar_campana";
    }

    @GetMapping("/crear")
    public String doCrearCampana(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        model.addAttribute("campana", new CampanaEntity());
        return "crear_editar/crear_editar_campana";
    }

    @PostMapping("/borrar")
    public  String doBorrarCampana(@RequestParam("idCampana") Integer idCampana,
                                   Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        CampanaEntity campanaDelete = campanasService.getReferenceById(idCampana);
        campanaDelete.eliminarParticipaciones();
        campanasService.borrarCampana(campanaDelete);
        return "redirect:/campanas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCampana(@RequestParam(value ="idCampana", required = false) Integer idCampana,
                                    @RequestParam(value="nombre", required = false) String nombre,
                                    @RequestParam(value="anyo", required = false) Integer anyo,
                                    @RequestParam(value="fecha-inicio", required = false) LocalDate fechaInic,
                                    @RequestParam(value="fecha-fin", required = false) LocalDate fechaFin,
            Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        CampanaEntity campana;
        if(idCampana == null){
            campana= new CampanaEntity();
        }else{
            campana = this.campanasService.getReferenceById(idCampana);
        }
        campana.setNombre(nombre);
        campana.setAno(anyo);
        campana.setDiaComienzo(fechaInic);
        campana.setDiaFinal(fechaFin);
        campanasService.guardarCampana(campana);

        return "redirect:/campanas/";
    }

}
