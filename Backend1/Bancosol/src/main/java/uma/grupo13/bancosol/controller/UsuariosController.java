package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.NotificacionEntity;
import uma.grupo13.bancosol.entity.RolEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.entity.ParticipaEntity;
import uma.grupo13.bancosol.services.NotificacionesService;
import uma.grupo13.bancosol.services.ParticipaService;
import uma.grupo13.bancosol.services.RolService;
import uma.grupo13.bancosol.services.UsuariosService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/usuarios")
public class UsuariosController {
    private final UsuariosService usuariosService;
    private final NotificacionesService notificacionesService;
    private final ParticipaService participaService;
    private final RolService rolService;


    @GetMapping("/")
    public String doUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user){
        if (user == null) return "redirect:/";
        model.addAttribute("paginaActual", "usuarios");
        List<UsuarioEntity> usuarios= usuariosService.listarUsuarios();
        model.addAttribute("users", usuarios);
        return "usuarios";
    }

    @GetMapping("/editar")
    public  String doEditarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        UsuarioEntity usuario=usuariosService.getReferenceById(id);
        model.addAttribute("usuario", usuario);
        List<RolEntity> roles= rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "crear_editar/crear_usuario";
    }

    @GetMapping("/crear")
    public  String doCrearUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        model.addAttribute("usuario", new UsuarioEntity());
        List<RolEntity> roles= rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "crear_editar/crear_usuario";
    }

    @PostMapping("/borrar")
    public  String doBorrarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user, @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        UsuarioEntity usuario=usuariosService.getReferenceById(id);
        List<NotificacionEntity> notficaciones=usuario.getNotificaciones();
        notificacionesService.deleteAll(notficaciones);

        List<ParticipaEntity> participacionesCoord = participaService.findByCoordinadorId(id);
        for (ParticipaEntity p : participacionesCoord) {
            p.setCoordinador(null);
        }
        participaService.guardarParticipaciones(participacionesCoord);

        usuario.deleteTiendas();
        usuariosService.borrarUsuario(usuario);
        return "redirect:/usuarios/";
    }

    @PostMapping("/guardar")
    public String doGuardarUsuarios(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user_session,
                                    @RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam("nombre") String nombre,
                                    @RequestParam("apellidos") String apellidos,
                                    @RequestParam("user") String user,
                                    @RequestParam("password") String password,
                                    @RequestParam("email") String email,
                                    @RequestParam(value = "telefono", required = false) String telefono,
                                    @RequestParam(value = "area", required = false) String area,
                                    @RequestParam("rol") Integer idRol) {
        if (user_session == null) return "redirect:/";

        UsuarioEntity usuario;
        if (id != null) {
            usuario = usuariosService.getReferenceById(id);
        } else {
            usuario = new UsuarioEntity();
        }

        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setUsuario(user);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setAreaAsignada(area);

        if (password != null && !password.isEmpty()) {
            usuario.setContrasena(password);
        } else if (id == null) {
            model.addAttribute("error", "La contraseña es obligatoria para nuevos usuarios.");
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", rolService.listarRoles());
            return "crear_editar/crear_usuario";
        }

        RolEntity rol = rolService.getReferenceById(idRol);
        usuario.setRol(rol);

        usuariosService.guardarUsuario(usuario);

        return "redirect:/usuarios/";
    }
}
