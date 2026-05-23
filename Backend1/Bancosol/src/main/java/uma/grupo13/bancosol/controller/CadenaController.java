package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;
import uma.grupo13.bancosol.services.CadenaService;

import java.util.List;

@Controller
@RequestMapping("/cadenas")
public class CadenaController {
    @Autowired
    protected CadenaService cadenaService;

    @GetMapping("/")
    public String doCadena(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model) {
        if (user == null) return "redirect:/";

        model.addAttribute("paginaActual", "cadenas");
        List<CadenaEntity> cadenaslist = cadenaService.listarCadenas();
        model.addAttribute("cadenas", cadenaslist);
        return "cadenas";
    }

    @GetMapping("/editar")
    public  String doEditarCadena(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model,
                                  @RequestParam("id") Integer id) {
        if (user == null) return "redirect:/";
        CadenaEntity cadena = cadenaService.getReferenceById(id);
        model.addAttribute("cadena", cadena);
        return "crear_editar/crear_editar_cadena";
    }

    @GetMapping("/crear")
    public  String doCrearCadena(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model) {
        if (user == null) return "redirect:/";
        return "crear_editar/crear_editar_cadena";
    }

    @PostMapping("/borrar")
    public  String doBorrarCadena(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model,
                                  @RequestParam("id") Integer id){
        if (user == null) return "redirect:/";
        CadenaEntity cadena = cadenaService.getReferenceById(id);
        cadena.eliminarTiendas();
        cadenaService.borrarCadena(cadena);
        return "redirect:/cadenas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCadena(@SessionAttribute(name = "user", required = false) UsuarioEntity user, Model model,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("codigo") String codigo,
                                   @RequestParam(value = "id",required = false) Integer id) {
        if (user == null) return "redirect:/";
        CadenaEntity cadena;
        if (id == null) {
            cadena = new CadenaEntity();
        } else {
            cadena = cadenaService.getReferenceById(id);
        }
        cadena.setNombre(nombre);
        cadena.setCodigo(codigo);
        cadenaService.guardarCadena(cadena);
        return "redirect:/cadenas/";
    }
}
