package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.TurnoEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.services.TurnosService;
import uma.grupo13.bancosol.services.VoluntariosService;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    private final VoluntariosService voluntariosService;
    private final TurnosService turnosService;


    @GetMapping("/")
    public String doColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        model.addAttribute("paginaActual", "colaboradores");
        model.addAttribute("colaboradores", voluntariosService.listarVoluntarios());
        model.addAttribute("localidades", voluntariosService.findLocalidadesDistintas());
        return "colaboradores";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@RequestParam(required = false) String tipo,
                            @RequestParam(required = false) String localidad,
                            Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        String localidadParam = (localidad == null || localidad.equals("all")) ? "" : localidad;

        List<VoluntarioBaseEntity> todos;
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
    public String doCrearColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        return "crear_editar/crear_editar_colaboradores";
    }

    @GetMapping("/editar")
    public String doEditarColaboradoresGet(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user,
                                           @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        VoluntarioBaseEntity voluntario = voluntariosService.buscarPorId(id);
        model.addAttribute("voluntario", voluntario);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/editar")
    public String doEditarColaboradoresPost(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user,
                                            @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        VoluntarioBaseEntity voluntario = voluntariosService.buscarPorId(id);
        model.addAttribute("voluntario", voluntario);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/crear")
    public String doCrearColaboradoresPost(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/borrar")
    public String doBorrarColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user,
                                        @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";

        List<TurnoEntity> turnos = turnosService.findByVoluntarioId(id);
        turnosService.deleteAll(turnos);

        voluntariosService.deleteById(id);
        return "redirect:/colaboradores/";
    }

    @PostMapping("/guardar")
    public String doGuardarColaboradores(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model,
                                        @RequestParam("tipo_colaborador") String tipo,
                                        @RequestParam("domicilio") String domicilio,
                                        @RequestParam("zona_geografica") String zonaGeografica,
                                        @RequestParam("codigo_postal") String codigoPostal,
                                        @RequestParam(value = "observaciones", required = false) String observaciones,
                                        @RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "nombre", required = false) String nombre,
                                        @RequestParam(value = "apellidos", required = false) String apellidos,
                                        @RequestParam(value = "nombre_asociacion", required = false) String nombreAsociacion,
                                        @RequestParam(value = "n_voluntarios", required = false) Integer nVoluntarios,
                                        @RequestParam(value = "confirmar", required = false) Boolean confirmar) {
        if (user == null) return "redirect:/";

        VoluntarioBaseEntity voluntario;

        if (id != null) {
            voluntario = voluntariosService.buscarPorId(id);
        } else {
            if ("fisico".equals(tipo)) {
                voluntario = new VoluntarioFisicoEntity();
            } else {
                voluntario = new VoluntarioEntidadEntity();
            }
        }

        if (confirmar == null) { confirmar = false; }

        voluntario.setAprobado(confirmar);
        voluntario.setDomicilio(domicilio);
        voluntario.setZonaGeografica(zonaGeografica);
        voluntario.setCodigoPostal(codigoPostal);


        if (voluntario instanceof VoluntarioFisicoEntity) {
            VoluntarioFisicoEntity fisico = (VoluntarioFisicoEntity) voluntario;
            fisico.setNombre(nombre);
            fisico.setApellidos(apellidos);
        } else if (voluntario instanceof VoluntarioEntidadEntity) {
            VoluntarioEntidadEntity entidad = (VoluntarioEntidadEntity) voluntario;
            entidad.setNombreAsociacion(nombreAsociacion);
            entidad.setNVoluntarios(nVoluntarios);
        }

        voluntariosService.guardarVoluntario(voluntario);
        return "redirect:/colaboradores/";
    }

}
