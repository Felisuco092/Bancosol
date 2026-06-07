package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.dto.RolDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.RolEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.services.NotificacionesService;
import uma.grupo13.bancosol.services.ParticipaService;
import uma.grupo13.bancosol.services.RolService;
import uma.grupo13.bancosol.services.UsuariosService;
import uma.grupo13.bancosol.services.utils.Permiso;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;
    private final NotificacionesService notificacionesService;
    private final ParticipaService participaService;
    private final RolService rolService;
    private final ValidaSesion validaSesion;


    @GetMapping("/")
    public String doUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user){
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.USUARIOS)) return "redirect:/dashboard";
        model.addAttribute("paginaActual", "usuarios");
        List<UsuarioDTO> usuarios= usuariosService.listarUsuarios();
        model.addAttribute("users", usuarios);
        return "usuarios";
    }

    @GetMapping("/editar")
    public  String doEditarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.USUARIOS)) return "redirect:/dashboard";
        UsuarioDTO usuario=usuariosService.getReferenceById(id);
        model.addAttribute("usuario", usuario);
        List<RolDTO> roles= rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "crear_editar/crear_usuario";
    }

    @GetMapping("/crear")
    public  String doCrearUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.USUARIOS)) return "redirect:/dashboard";
        model.addAttribute("usuario", new UsuarioDTO());
        List<RolDTO> roles= rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "crear_editar/crear_usuario";
    }

    @PostMapping("/borrar")
    public  String doBorrarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.USUARIOS)) return "redirect:/dashboard";
        UsuarioDTO usuario=usuariosService.getReferenceById(id);
        List<NotificacionDTO> notficaciones=usuariosService.getNotificaciones(id);
        notificacionesService.deleteAll(notficaciones);

        participaService.editarParticipaCoordinador(id);

        usuariosService.borrarUsuario(id);
        return "redirect:/usuarios/";
    }

    @PostMapping("/guardar")
    public String doGuardarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user_session,
                                    @RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("apellidos") String apellidos,
                                    @RequestParam("user") String user,
                                    @RequestParam(value = "password", required = false) String password,
                                    @RequestParam("email") String email,
                                    @RequestParam(value = "telefono", required = false) String telefono,
                                    @RequestParam(value = "area", required = false) String area,
                                    @RequestParam("rol") Integer idRol) {
        if (user_session == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user_session.getRol().getId(), Permiso.USUARIOS)) return "redirect:/dashboard";

        usuariosService.guardarUsuario(id, nombre, apellidos, user, email, telefono, area, password, idRol);

        return "redirect:/usuarios/";
    }
}
