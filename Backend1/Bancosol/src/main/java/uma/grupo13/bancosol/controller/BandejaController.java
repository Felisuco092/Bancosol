package uma.grupo13.bancosol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.NotificacionesService;

import java.util.List;

@Controller
@RequestMapping("/bandeja")
public class BandejaController {
    @Autowired
    protected NotificacionesService notificacionesService;

    @GetMapping("/")
    public String doBandeja(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user){
        if (user == null) return "redirect:/";

        model.addAttribute("paginaActual", "bandeja");
        List<NotificacionDTO> notificacionList = this.notificacionesService.listarNotificaciones();
        model.addAttribute("notificacionesList", notificacionList);

        return "bandeja";
    }

    @PostMapping("/mensaje")
    public String doMensaje(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                            @RequestParam("idMensaje") Integer idMensaje) {
        if (user == null) return "redirect:/";
        NotificacionDTO notificacion = notificacionesService.getReferenceById(idMensaje);
        model.addAttribute("notificacion", notificacion);
        return "mensajes/ver_mensaje";
    }
}
