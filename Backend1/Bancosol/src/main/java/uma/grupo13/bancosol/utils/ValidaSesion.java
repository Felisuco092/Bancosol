package uma.grupo13.bancosol.utils;

import jakarta.servlet.http.HttpSession;
import uma.grupo13.bancosol.entity.RolEntity;
import uma.grupo13.bancosol.entity.UsuarioEntity;

public class ValidaSesion {
    public static boolean verificarSesion(HttpSession session){
        return session.getAttribute("user") != null;
    }
    public static UsuarioEntity usuarioSesion(HttpSession session){
        return (UsuarioEntity) session.getAttribute("user");
    }
    public static RolEntity rolUsuario(HttpSession session){
        UsuarioEntity user= (UsuarioEntity) session.getAttribute("user");
        return user.getRol();
    }
}
