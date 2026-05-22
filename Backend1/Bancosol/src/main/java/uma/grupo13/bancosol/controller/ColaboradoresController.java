package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dao.TurnoRepository;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.entity.*;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    @Autowired
    protected VoluntariosRepository voluntariosRepository;

    @Autowired
    protected TurnoRepository turnoRepository;


    @GetMapping("/")
    public String doColaboradores(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user) {
        if (user == null) return "redirect:/";

        model.addAttribute("paginaActual", "colaboradores");
        model.addAttribute("colaboradores", voluntariosRepository.findAll());
        model.addAttribute("localidades", voluntariosRepository.findLocalidadesDistintas());
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
            todos = voluntariosRepository.findAllByLocalidad(localidadParam);
        } else if (tipo.equals("true")) {
            todos = voluntariosRepository.findBaseFisicos(localidadParam);
        } else if (tipo.equals("false")) {
            todos = voluntariosRepository.findBaseEntidades(localidadParam);
        } else {
            todos = voluntariosRepository.findPendientes(localidadParam);
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
        VoluntarioBaseEntity voluntario = voluntariosRepository.findById(id).orElse(null);
        model.addAttribute("voluntario", voluntario);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/editar")
    public String doEditarColaboradoresPost(Model model, @SessionAttribute(name = "user", required = false) UsuarioEntity user,
                                            @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        VoluntarioBaseEntity voluntario = voluntariosRepository.findById(id).orElse(null);
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

        List<TurnoEntity> turnos = turnoRepository.findByVoluntarioId(id);
        turnoRepository.deleteAll(turnos);

        voluntariosRepository.deleteById(id);
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
            voluntario = voluntariosRepository.findById(id).orElse(null);
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

        voluntariosRepository.save(voluntario);
        return "redirect:/colaboradores/";
    }

}
