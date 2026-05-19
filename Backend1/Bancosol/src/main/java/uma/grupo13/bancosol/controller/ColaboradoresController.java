package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.VoluntariosRepository;
import uma.grupo13.bancosol.entity.VoluntarioBaseEntity;
import uma.grupo13.bancosol.entity.VoluntarioEntidadEntity;
import uma.grupo13.bancosol.entity.VoluntarioFisicoEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/colaboradores")
public class ColaboradoresController {
    @Autowired
    protected VoluntariosRepository voluntariosRepo;

    @GetMapping("/")
    public String doColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "colaboradores");
        model.addAttribute("colaboradores", voluntariosRepo.findAll());
        model.addAttribute("localidades", voluntariosRepo.findLocalidadesDistintas());
        return "colaboradores";
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@RequestParam(required = false) String tipo,
                            @RequestParam(required = false) String localidad,
                            Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        String localidadParam = (localidad == null || localidad.equals("all")) ? "" : localidad;

        List<VoluntarioBaseEntity> todos;
        if (tipo == null || tipo.equals("all")) {
            todos = voluntariosRepo.findAllByLocalidad(localidadParam);
        } else if (tipo.equals("true")) {
            todos = voluntariosRepo.findBaseFisicos(localidadParam);
        } else if (tipo.equals("false")) {
            todos = voluntariosRepo.findBaseEntidades(localidadParam);
        } else {
            todos = voluntariosRepo.findPendientes(localidadParam);
        }

        model.addAttribute("colaboradores", todos);
        return "tablas/colaboradores";
    }

    @GetMapping("/crear")
    public String doCrearColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "crear_editar/crear_editar_colaboradores";
    }

    @GetMapping("/editar")
    public String doEditarColaboradoresGet(Model model, HttpSession session,
                                           @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        VoluntarioBaseEntity voluntario = voluntariosRepo.findById(id).orElse(null);
        model.addAttribute("voluntario", voluntario);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/editar")
    public String doEditarColaboradoresPost(Model model, HttpSession session,
                                            @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        VoluntarioBaseEntity voluntario = voluntariosRepo.findById(id).orElse(null);
        model.addAttribute("voluntario", voluntario);
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/crear")
    public String doCrearColaboradoresPost(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "crear_editar/crear_editar_colaboradores";
    }

    @PostMapping("/borrar")
    public String doBorrarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/colaboradores/";
    }

    @PostMapping("/guardar")
    public String doGuardarColaboradores(HttpSession session, Model model,
                                        @RequestParam("tipo_colaborador") String tipo,
                                        @RequestParam("domicilio") String domicilio,
                                        @RequestParam("zona_geografica") String zonaGeografica,
                                        @RequestParam("codigo_postal") String codigoPostal,
                                        @RequestParam(value = "observaciones", required = false) String observaciones,
                                        @RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "nombre", required = false) String nombre,
                                        @RequestParam(value = "apellidos", required = false) String apellidos,
                                        @RequestParam(value = "nombre_asociacion", required = false) String nombreAsociacion,
                                        @RequestParam(value = "n_voluntarios", required = false) Integer nVoluntarios) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        VoluntarioBaseEntity voluntario;

        if (id != null) {
            voluntario = voluntariosRepo.findById(id).orElse(null);
        } else {
            if ("fisico".equals(tipo)) {
                voluntario = new VoluntarioFisicoEntity();
            } else {
                voluntario = new VoluntarioEntidadEntity();
            }
        }

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

        voluntariosRepo.save(voluntario);
        return "redirect:/colaboradores/";
    }

}
