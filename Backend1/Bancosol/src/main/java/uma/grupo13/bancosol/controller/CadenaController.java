package uma.grupo13.bancosol.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uma.grupo13.bancosol.dao.CadenaRepository;
import uma.grupo13.bancosol.entity.CadenaEntity;
import uma.grupo13.bancosol.utils.ValidaSesion;

import java.util.List;

@Controller
@RequestMapping("/cadenas")
public class CadenaController {
    @Autowired
    protected CadenaRepository  cadenaRepository;

    @GetMapping("/")
    public String doCadena(HttpSession session, Model model) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";

        model.addAttribute("paginaActual", "cadenas");
        List<CadenaEntity> cadenaslist = cadenaRepository.findAll();
        model.addAttribute("cadenas", cadenaslist);
        return "cadenas";
    }

    @GetMapping("/editar")
    public  String doEditarCadena(HttpSession session, Model model,
                                  @RequestParam("id") Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CadenaEntity cadena = cadenaRepository.getReferenceById(id);
        model.addAttribute("cadena", cadena);
        return "crear_editar/crear_editar_cadena";
    }

    @GetMapping("/crear")
    public  String doCrearCadena(HttpSession session, Model model) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        return "crear_editar/crear_editar_cadena";
    }

    @PostMapping("/borrar")
    public  String doBorrarCadena(HttpSession session, Model model,
                                  @RequestParam("id") Integer id){
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CadenaEntity cadena = cadenaRepository.getReferenceById(id);
        cadena.eliminarTiendas();
        cadenaRepository.delete(cadena);
        return "redirect:/cadenas/";
    }

    @PostMapping("/guardar")
    public  String doGuardarCadena(HttpSession session, Model model,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("codigo") String codigo,
                                   @RequestParam(value = "id",required = false) Integer id) {
        if (!ValidaSesion.verificarSesion(session)) return "redirect:/";
        CadenaEntity cadena;
        if (id == null) {
            cadena = new CadenaEntity();
        } else {
            cadena = cadenaRepository.getReferenceById(id);
        }
        cadena.setNombre(nombre);
        cadena.setCodigo(codigo);
        cadenaRepository.save(cadena);
        return "redirect:/cadenas/";
    }
}
