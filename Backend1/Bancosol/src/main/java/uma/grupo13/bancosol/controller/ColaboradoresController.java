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

    @PostMapping("/editar")
    public String doEditarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/crear")
    public String doCrearColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/borrar")
    public String doBorrarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "";
    }

    @PostMapping("/guardar")
    public String doGuardarColaboradores(Model model, HttpSession session) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "redirect:/colaboradores";
    }
}
