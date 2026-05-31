package uma.grupo13.bancosol.services.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import uma.grupo13.bancosol.dto.UsuarioDTO;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidaSesion {

    private static final Map<Integer, Map<Permiso, Boolean>> permisos = new HashMap<>();
    static {
        Map<Permiso, Boolean> permisosRol0 = new HashMap<>();
        permisosRol0.put(Permiso.USUARIOS, true);
        permisosRol0.put(Permiso.TIENDAS, true);
        permisosRol0.put(Permiso.CADENAS, true);
        permisosRol0.put(Permiso.CAMPANAS, true);
        permisosRol0.put(Permiso.TURNOS, true);
        permisosRol0.put(Permiso.COLABORADORES, true);
        permisosRol0.put(Permiso.INCIDENCIAS, true);
        permisosRol0.put(Permiso.EDITAR_TIENDA, true);
        permisosRol0.put(Permiso.EDITAR_TURNOS, true);
        permisosRol0.put(Permiso.EDITAR_COLABORADORES, true);
        permisosRol0.put(Permiso.BORRAR_COLABORADORES, true);
        permisosRol0.put(Permiso.CONFIRMAR_COLABORADORES, true);
        permisos.put(1, permisosRol0);

        Map<Permiso, Boolean> permisosRol1 = new HashMap<>();
        permisosRol1.put(Permiso.USUARIOS, false);
        permisosRol1.put(Permiso.TIENDAS, true);
        permisosRol1.put(Permiso.CADENAS, false);
        permisosRol1.put(Permiso.CAMPANAS, false);
        permisosRol1.put(Permiso.TURNOS, true);
        permisosRol1.put(Permiso.COLABORADORES, true);
        permisosRol1.put(Permiso.INCIDENCIAS, true);
        permisosRol1.put(Permiso.EDITAR_TIENDA, false);
        permisosRol1.put(Permiso.EDITAR_TURNOS, true);
        permisosRol1.put(Permiso.EDITAR_COLABORADORES, true);
        permisosRol1.put(Permiso.BORRAR_COLABORADORES, false);
        permisosRol1.put(Permiso.CONFIRMAR_COLABORADORES, false);
        permisos.put(2, permisosRol1);

        Map<Permiso, Boolean> permisosRol2 = new HashMap<>();
        permisosRol2.put(Permiso.USUARIOS, false);
        permisosRol2.put(Permiso.TIENDAS, true);
        permisosRol2.put(Permiso.CADENAS, false);
        permisosRol2.put(Permiso.CAMPANAS, false);
        permisosRol2.put(Permiso.TURNOS, true);
        permisosRol2.put(Permiso.COLABORADORES, true);
        permisosRol2.put(Permiso.INCIDENCIAS, true);
        permisosRol2.put(Permiso.EDITAR_TIENDA, false);
        permisosRol2.put(Permiso.EDITAR_TURNOS, false);
        permisosRol2.put(Permiso.EDITAR_COLABORADORES, false);
        permisosRol2.put(Permiso.BORRAR_COLABORADORES, false);
        permisosRol2.put(Permiso.CONFIRMAR_COLABORADORES, false);
        permisos.put(3, permisosRol2);

        Map<Permiso, Boolean> permisosRol3 = new HashMap<>();
        permisosRol3.put(Permiso.USUARIOS, false);
        permisosRol3.put(Permiso.TIENDAS, true);
        permisosRol3.put(Permiso.CADENAS, false);
        permisosRol3.put(Permiso.CAMPANAS, false);
        permisosRol3.put(Permiso.TURNOS, true);
        permisosRol3.put(Permiso.COLABORADORES, true);
        permisosRol3.put(Permiso.INCIDENCIAS, true);
        permisosRol3.put(Permiso.EDITAR_TIENDA, false);
        permisosRol3.put(Permiso.EDITAR_TURNOS, false);
        permisosRol3.put(Permiso.EDITAR_COLABORADORES, false);
        permisosRol3.put(Permiso.BORRAR_COLABORADORES, false);
        permisosRol3.put(Permiso.CONFIRMAR_COLABORADORES, false);
        permisos.put(4, permisosRol3);

        Map<Permiso, Boolean> permisosRol4 = new HashMap<>();
        permisosRol4.put(Permiso.USUARIOS, false);
        permisosRol4.put(Permiso.TIENDAS, true);
        permisosRol4.put(Permiso.CADENAS, false);
        permisosRol4.put(Permiso.CAMPANAS, false);
        permisosRol4.put(Permiso.TURNOS, true);
        permisosRol4.put(Permiso.COLABORADORES, true);
        permisosRol4.put(Permiso.INCIDENCIAS, false);
        permisosRol4.put(Permiso.EDITAR_TIENDA, false);
        permisosRol4.put(Permiso.EDITAR_TURNOS, false);
        permisosRol4.put(Permiso.EDITAR_COLABORADORES, false);
        permisosRol4.put(Permiso.BORRAR_COLABORADORES, false);
        permisosRol4.put(Permiso.CONFIRMAR_COLABORADORES, false);
        permisos.put(5, permisosRol4);
    }

    public boolean verificarSesion(HttpSession session){
        return session.getAttribute("user") != null;
    }

    public Integer rolUsuario(HttpSession session){
        UsuarioDTO user= (UsuarioDTO) session.getAttribute("user");
        return user.getRol().getId();
    }

    public Boolean tienePermiso(Integer rol, Permiso permiso){
        Map<Permiso, Boolean> rolPermisos = permisos.get(rol);
        if (rolPermisos == null) return false;
        Boolean permisoValue = rolPermisos.get(permiso);
        return permisoValue != null && permisoValue;
    }
}
