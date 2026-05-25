<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.RolEntity" %>
<%@ page import="uma.grupo13.bancosol.dto.UsuarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.RolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<UsuarioDTO> usuarios=(List<UsuarioDTO>) request.getAttribute("users");
%>

<table>
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Apellidos</th>
        <th>Email</th>
        <th>Teléfono</th>
        <th>Rol</th>
        <th>Área Asignada (Municipio)</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%
        for(UsuarioDTO u:usuarios){
            RolDTO rol= u.getRol();
            String rolNombre = rol.getNombre();
            String rolClass = rolNombre.toLowerCase()
                .replace("á", "a")
                .replace("é", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ú", "u")
                .replace(" ", "-");
    %>
        <tr>
            <td><%=u.getNombre()%></td>
            <td><%=u.getApellidos()%></td>
            <td><%=u.getEmail()%></td>
            <td><%=u.getTelefono()%></td>
            <td><span class="badge-rol badge-<%=rolClass%>"><%=rol.getNombre()%></span></td>
            <td><%=u.getAreaAsignada()%></td>
            <td>
                <a href="/usuarios/editar?id=<%=u.getId()%>"><button class="btn btn-primary btn-sm">Editar</button></a>
                <form action="/usuarios/borrar" method="POST" onsubmit="return confirm('¿Seguro que desea eliminar este usuario?')">
                    <input type="hidden" name="id" value="<%=u.getId()%>">
                    <input type="submit" class="btn btn-danger btn-sm" value="Borrar">
                </form>
            </td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>