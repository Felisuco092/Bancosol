package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.NotificacionRepository;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/bandeja")
public class BandejaController {
    @Autowired
    protected NotificacionRepository notificacionRepository;

    @GetMapping("/")
    public String doBandeja(Model model, HttpSession session){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "bandeja");
        List<NotificacionEntity> notificacionList = this.notificacionRepository.findAll();
        model.addAttribute("notificacionesList", notificacionList);


        return "bandeja";
    }

    @PostMapping("/mensaje")
    public String doMensaje(Model model, HttpSession session,
                            @RequestParam("idMensaje") Integer idMensaje) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        NotificacionEntity notificacion = notificacionRepository.getReferenceById(idMensaje);
        model.addAttribute("notificacion", notificacion);
        return "mensajes/ver_mensaje";
    }

}
