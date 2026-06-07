package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.dto.CadenaDTO;
import uma.grupo13.bancosol.dto.UsuarioDTO;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.CadenaService;
import uma.grupo13.bancosol.services.utils.Permiso;
import uma.grupo13.bancosol.services.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/cadenas")
public class CadenaController {
    @Autowired
    protected CadenaService cadenaService;
    @Autowired
    protected ValidaSesion validaSesion;


    @GetMapping("/")
    public String doCadena(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CADENAS)) return "redirect:/dashboard";

        model.addAttribute("paginaActual", "cadenas");
        List<CadenaDTO> cadenaslist = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenaslist);
        return "cadenas";
    }

    @GetMapping("/editar")
    public  String doEditarCadena(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model,
                                  @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CADENAS)) return "redirect:/dashboard";
        CadenaDTO cadena = cadenaService.getReferenceById(id);
        model.addAttribute("cadena", cadena);
        return "crear_editar/crear_editar_cadena";
    }

    @GetMapping("/crear")
    public  String doCrearCadena(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CADENAS)) return "redirect:/dashboard";
        return "crear_editar/crear_editar_cadena";
    }

    @PostMapping("/borrar")
    public  String doBorrarCadena(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model,
                                  @RequestParam("id") Integer id){
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CADENAS)) return "redirect:/dashboard";
        cadenaService.borrarCadenaId(id);
        return "redirect:/cadenas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCadena(@SessionAttribute(name = "user", required = false) UsuarioDTO user, Model model,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("codigo") String codigo,
                                   @RequestParam(value = "id",required = false) Integer id) {
        if (user == null) return "redirect:/";
        if (!validaSesion.tienePermiso(user.getRol().getId(), Permiso.CADENAS)) return "redirect:/dashboard";
        try{
            cadenaService.guardarCadena(id, nombre, codigo);
        }catch (Exception e){
            e.printStackTrace();
            CadenaDTO cadena = new CadenaDTO();
            cadena.setId(id);
            cadena.setNombre(nombre);
            cadena.setCodigo(codigo);
            model.addAttribute("cadena", cadena);
            model.addAttribute("error", "Error al guardar la cadena: " + e.getMessage());
            return "crear_editar/crear_editar_cadena";
        }
        return "redirect:/cadenas/";
    }
}
