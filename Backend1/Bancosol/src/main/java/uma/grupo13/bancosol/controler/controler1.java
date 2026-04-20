package uma.grupo13.bancosol.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uma.grupo13.bancosol.dao.CampanaRepository;
import uma.grupo13.bancosol.entity.CampanaEntity;

import java.util.List;

@Controller
public class controler1 {
    @Autowired
    protected CampanaRepository campanaRepository;


    @GetMapping("/")
    public String doLogin() {
        return "index";
    }

    @GetMapping("/bandeja")
    public String doStart() {
        return "bandeja";
    }

    @GetMapping("/campanas")
    public String doCampanas(Model model) {
        List<CampanaEntity> campanas = campanaRepository.findAll();
        for (CampanaEntity campana : campanas) {
            System.out.println(campana.getNombre());
        }
        model.addAttribute("campanas", campanas);
        return "campanas";
    }

    @GetMapping("/colaboradores")
    public String doColaboradores() {
        return "colaboradores";
    }

    @GetMapping("/dashboard")
    public String doDashboard() {
        return "dashboard";
    }

    @GetMapping("/tiendas")
    public String doTiendas() {
        return "tiendas";
    }

    @GetMapping("/turnos")
    public String doTurnos(){
        return "turnos";
    }

    @GetMapping("/usuarios")
    public String doUsuarios(){
        return "usuarios";
    }


}
