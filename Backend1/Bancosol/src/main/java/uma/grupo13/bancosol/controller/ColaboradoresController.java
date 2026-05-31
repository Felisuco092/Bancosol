package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.NotificacionDTO;
import uma.grupo13.bancosol.dto.TurnoDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.dto.VoluntarioDTO;

import uma.grupo13.bancosol.services.UsuariosService;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.services.NotificacionesService;

import uma.grupo13.bancosol.services.TurnosService;
import uma.grupo13.bancosol.services.VoluntariosService;
import uma.grupo13.bancosol.services.utils.Permiso;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    private final VoluntariosService voluntariosService;
    private final TurnosService turnosService;
    private final UsuariosService usuariosService;
    private final ValidaSesion validaSesion;
    private final NotificacionesService  notificacionesService;

    @GetMapping("/")
    public String doColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.COLABORADORES)) return "redirect:/dashboard";

        model.addAttribute("paginaActual", "colaboradores");
        model.addAttribute("colaboradores", voluntariosService.listarVoluntarios());
        model.addAttribute("localidades", voluntariosService.findLocalidadesDistintas());
        return "colaboradores";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@RequestParam(required = false) String tipo,
                            @RequestParam(required = false) String localidad,
                            Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.COLABORADORES)) return "redirect:/dashboard";

        String localidadParam = (localidad == null || localidad.equals("all")) ? "" : localidad;

        List<VoluntarioDTO> todos;
        if (tipo == null || tipo.equals("all")) {
            todos = voluntariosService.findAllByLocalidad(localidadParam);

        } else if (tipo.equals("true")) {
            todos = voluntariosService.findBaseFisicos(localidadParam);
        } else if (tipo.equals("false")) {
            todos = voluntariosService.findBaseEntidades(localidadParam);
        } else {
            todos = voluntariosService.findPendientes(localidadParam);
        }

        model.addAttribute("colaboradores", todos);
        return "tablas/colaboradores";
    }

    @GetMapping("/crear")
    public String doCrearColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_COLABORADORES)) return "redirect:/dashboard";
        model.addAttribute("voluntario", new VoluntarioDTO());
        List<UsuarioDTO> responsablesEntidad = usuariosService.findResponsablesEntidad();
        model.addAttribute("responsablesEntidad", responsablesEntidad);
        return "crear_editar/crear_editar_colaboradores";
    }

    @GetMapping("/editar")
    public String doEditarColaboradoresGet(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                                           @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_COLABORADORES)) return "redirect:/dashboard";

        VoluntarioDTO voluntario = voluntariosService.buscarPorId(id);
        model.addAttribute("voluntario", voluntario);
        List<UsuarioDTO> responsablesEntidad = usuariosService.findResponsablesEntidad();
        model.addAttribute("responsablesEntidad", responsablesEntidad);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/editar")
    public String doEditarColaboradoresPost(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                                            @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_COLABORADORES)) return "redirect:/dashboard";

        VoluntarioDTO voluntario = voluntariosService.buscarPorId(id);
        model.addAttribute("voluntario", voluntario);
        List<UsuarioDTO> responsablesEntidad = usuariosService.findResponsablesEntidad();
        model.addAttribute("responsablesEntidad", responsablesEntidad);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/crear")
    public String doCrearColaboradoresPost(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_COLABORADORES)) return "redirect:/dashboard";

        model.addAttribute("voluntario", new VoluntarioDTO());
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/borrar")
    public String doBorrarColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioDTO user,
                                        @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.BORRAR_COLABORADORES)) return "redirect:/dashboard";


        List<TurnoDTO> turnos = turnosService.findByVoluntarioId(id);
        turnosService.deleteAll(turnos);

        voluntariosService.deleteById(id);
        return "redirect:/colaboradores/";
    }

    @PostMapping("/guardar")
    public String doGuardarColaboradores(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model,
                                        @RequestParam("tipo_colaborador") String tipo,
                                        @RequestParam("domicilio") String domicilio,
                                        @RequestParam("zona_geografica") String zonaGeografica,
                                        @RequestParam("codigo_postal") String codigoPostal,
                                        @RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "nombre", required = false) String nombre,
                                        @RequestParam(value = "apellidos", required = false) String apellidos,
                                        @RequestParam(value = "nombre_asociacion", required = false) String nombreAsociacion,
                                        @RequestParam(value = "n_voluntarios", required = false) Integer nVoluntarios,
                                        @RequestParam(value = "confirmar", required = false) Boolean confirmar,
                                        @RequestParam(value = "responsableEntidad", required = false) Integer idResponsableEntidad) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.EDITAR_COLABORADORES)) return "redirect:/dashboard";



        VoluntarioDTO voluntarioDTO = voluntariosService.guardarVoluntario(id, tipo, domicilio, zonaGeografica, codigoPostal, nombre,
                apellidos, nombreAsociacion, nVoluntarios, confirmar, idResponsableEntidad);
        if(!user.getRol().getId().equals(1) && id==null){// el admin no es el que crea al nuevo colaborador
            notificacionesService.crearNotificacionColabYEnviar(nombre, apellidos, user.getUsuario());

        }

        return "redirect:/colaboradores/";
    }

}
