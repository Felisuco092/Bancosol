package uma.grupo13.bancosol.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class controler1 {
    private String user=null;
    @GetMapping("/")
    public String doStart() {
        if (user==null){
            return "a";
        }
        return "b";
    }
    @GetMapping("/login")
    public String doLogin() {
        return "login";
    }
    @PostMapping("/vadilarLogin")
    public String doValidar() {
        return "redirect:/";
    }

}
