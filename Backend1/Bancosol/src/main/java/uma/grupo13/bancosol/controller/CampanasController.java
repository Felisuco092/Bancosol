package uma.grupo13.bancosol.controller;

import jakarta.persistence.PreUpdate;
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

import java.time.LocalDate;
import java.util.Date;
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

    @GetMapping("/editar")
    public  String doEditarCampana(@RequestParam("id") Integer idCampana,
            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CampanaEntity campanaEdit = campanaRepository.getReferenceById(idCampana);
        model.addAttribute("campana", campanaEdit);
        return "crear_editar/crear_editar_campana";
    }

    @GetMapping("/crear")
    public String doCrearCampana(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        model.addAttribute("campana", new CampanaEntity());
        return "crear_editar/crear_editar_campana";
    }

    @PostMapping("/borrar")
    public  String doBorrarCampana(@RequestParam("id") Integer idCampana,
            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CampanaEntity campanaDelete = campanaRepository.getReferenceById(idCampana);
        campanaDelete.eliminarParticipaciones();
        campanaRepository.delete(campanaDelete);
        return "redirect:/campanas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCampana(@RequestParam(value ="idCampana", required = false) Integer idCampana,
                                    @RequestParam(value="nombre", required = false) String nombre,
                                    @RequestParam(value="anyo", required = false) Integer anyo,
                                    @RequestParam(value="fecha-inicio", required = false) LocalDate fechaInic,
                                    @RequestParam(value="fecha-fin", required = false) LocalDate fechaFin,
            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CampanaEntity campana;
        if(idCampana == null){
            campana= new CampanaEntity();
        }else{
            campana = this.campanaRepository.getReferenceById(idCampana);
        }
        campana.setNombre(nombre);
        campana.setAno(anyo);
        campana.setDiaComienzo(fechaInic);
        campana.setDiaFinal(fechaFin);
        campanaRepository.save(campana);

        return "redirect:/campanas/";
    }

}
