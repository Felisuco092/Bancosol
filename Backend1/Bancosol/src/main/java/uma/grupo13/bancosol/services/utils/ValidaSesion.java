package uma.grupo13.bancosol.services.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dto.UsuarioDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidaSesion {

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
        permisos.put(1, permisosRol0);

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
        permisos.put(2, permisosRol1);

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
        permisos.put(3, permisosRol2);

        Map<String, Boolean> permisosRol3 = new HashMap<>();
        permisosRol3.put("usuarios", false);
        permisosRol3.put("tiendas", true);
        permisosRol3.put("cadenas", false);
        permisosRol3.put("campanas", false);
        permisosRol3.put("turnos", true);
        permisosRol3.put("colaboradores", true);
        permisosRol3.put("incidencias", true);
        permisosRol3.put("editarTienda", false);
        permisosRol3.put("editarTurnos", false);
        permisosRol3.put("editarColaboradores", false);
        permisosRol3.put("borrarColaboradores", false);
        permisosRol3.put("confirmarColaboradores", false);
        permisos.put(4, permisosRol3);

        Map<String, Boolean> permisosRol4 = new HashMap<>();
        permisosRol4.put("usuarios", false);
        permisosRol4.put("tiendas", true);
        permisosRol4.put("cadenas", false);
        permisosRol4.put("campanas", false);
        permisosRol4.put("turnos", true);
        permisosRol4.put("colaboradores", true);
        permisosRol4.put("incidencias", false);
        permisosRol4.put("editarTienda", false);
        permisosRol4.put("editarTurnos", false);
        permisosRol4.put("editarColaboradores", false);
        permisosRol4.put("borrarColaboradores", false);
        permisosRol4.put("confirmarColaboradores", false);
        permisos.put(5, permisosRol4);
    }

    public boolean verificarSesion(HttpSession session){
        return session.getAttribute("user") != null;
    }

    public Integer rolUsuario(HttpSession session){
        UsuarioDTO user= (UsuarioDTO) session.getAttribute("user");
        return user.getRol().getId();
    }

    public Boolean tienePermiso(Integer rol, String pagina){
        Boolean permiso= permisos.get(rol).get(pagina);
        if(permiso==null) return false;
        return permiso;
    }
}
