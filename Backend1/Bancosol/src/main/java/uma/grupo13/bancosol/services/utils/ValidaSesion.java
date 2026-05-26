package uma.grupo13.bancosol.services.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dto.UsuarioDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidaSesion {

    //El map lo ha hecho la ia(nos daba pereza :v)
    private static final Map<Integer, Map<String, Boolean>> permisos = new HashMap<>();
    static {
        Map<String, Boolean> permisosRol0 = new HashMap<>();
        permisosRol0.put("usuarios", true);
        permisosRol0.put("tiendas", true);
        permisosRol0.put("cadenas", true);
        permisosRol0.put("campanas", true);
        permisosRol0.put("turnos", true);
        permisosRol0.put("colaboradores", true);
        permisosRol0.put("incidencias", true);
        permisosRol0.put("editarTienda", true);
        permisosRol0.put("editarTurnos", true);
        permisosRol0.put("editarColaboradores", true);
        permisosRol0.put("borrarColaboradores", true);
        permisosRol0.put("confirmarColaboradores", true);
        permisos.put(0, permisosRol0);

        Map<String, Boolean> permisosRol1 = new HashMap<>();
        permisosRol1.put("usuarios", false);
        permisosRol1.put("tiendas", true);
        permisosRol1.put("cadenas", false);
        permisosRol1.put("campanas", false);
        permisosRol1.put("turnos", true);
        permisosRol1.put("colaboradores", true);
        permisosRol1.put("incidencias", true);
        permisosRol1.put("editarTienda", false);
        permisosRol1.put("editarTurnos", true);
        permisosRol1.put("editarColaboradores", true);
        permisosRol1.put("borrarColaboradores", false);
        permisosRol1.put("confirmarColaboradores", false);
        permisos.put(1, permisosRol1);

        Map<String, Boolean> permisosRol2 = new HashMap<>();
        permisosRol2.put("usuarios", false);
        permisosRol2.put("tiendas", true);
        permisosRol2.put("cadenas", false);
        permisosRol2.put("campanas", false);
        permisosRol2.put("turnos", true);
        permisosRol2.put("colaboradores", true);
        permisosRol2.put("incidencias", true);
        permisosRol2.put("editarTienda", false);
        permisosRol2.put("editarTurnos", false);
        permisosRol2.put("editarColaboradores", false);
        permisosRol2.put("borrarColaboradores", false);
        permisosRol2.put("confirmarColaboradores", false);
        permisos.put(2, permisosRol2);
    }

    public boolean verificarSesion(HttpSession session){
        return session.getAttribute("user") != null;
    }

    public Integer rolUsuario(HttpSession session){
        UsuarioDTO user= (UsuarioDTO) session.getAttribute("user");
        return user.getRol().getId();
    }

    public Boolean tienePermiso(Integer rol, String pagina){
        return permisos.get(rol).get(pagina);
    }
}
