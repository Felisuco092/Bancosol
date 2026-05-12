<%@ page import="uma.grupo13.bancosol.entity.UsuarioEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.entity.RolEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<UsuarioEntity> usuarios=(List<UsuarioEntity>) request.getAttribute("users");
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
        for(UsuarioEntity u:usuarios){
            RolEntity rol= u.getRol();
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
                <button class="btn btn-primary btn-sm" onclick="editarUsuario(<%=u.getId()%>)">Editar</button>
                <button class="btn btn-danger btn-sm" onclick="borrarUsuario(<%=u.getId()%>)">Baja</button>
            </td>
        </tr>
    <%
        }
    %>
    </tbody>
</table>