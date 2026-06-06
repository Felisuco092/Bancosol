<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<VoluntarioDTO> cols = (List<VoluntarioDTO>) request.getAttribute("colaboradores");
    Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
%>
<table>
    <thead>
    <tr>
        <th>Entidad / Nombre</th>
        <th>Tipo</th>
        <th>Zona Geográfica</th>
        <th>C.P.</th>
        <th>Voluntarios</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody id="colaboradores-tbody">
    <% if (cols != null) for (VoluntarioDTO v : cols) {
        if (!("FISICO".equals(v.getTipo())) && !("ENTIDAD".equals(v.getTipo()))) continue;
        boolean esPersona = "FISICO".equals(v.getTipo());
        boolean pendiente = v.getAprobado() == null || !v.getAprobado();
        String badgeClass = pendiente ? "badge-confirmar" : (esPersona ? "badge-persona" : "badge-entidad");
        String badgeText = pendiente ? "Por confirmar" : (esPersona ? "Persona Física" : "Entidad / Grupo");
        String nombre = v.getNombreDisplay();
        int nVol = v.getNumeroVoluntariosDisplay();
    %>
        <tr data-es-persona="<%=esPersona%>" data-localidad="<%=v.getZonaGeografica()%>">
            <td><%=nombre%></td>
            <td class="tipo-cell">
                <span class="badge <%=badgeClass%>"><%=badgeText%></span>
            </td>
            <td><%=v.getZonaGeografica()%></td>
            <td><%=v.getCodigoPostal()%></td>
            <td><%=nVol%></td>
            <td>
                <% if (Boolean.TRUE.equals(permisos.get(Permiso.EDITAR_COLABORADORES))) { %>
                    <a href="/colaboradores/editar?id=<%=v.getId()%>"><button class="btn btn-primary btn-sm">Editar</button></a>
                <% } %>
                <% if (Boolean.TRUE.equals(permisos.get(Permiso.BORRAR_COLABORADORES))) { %>
                    <form action="/colaboradores/borrar" method="post"
                          onsubmit="return confirm('¿Eliminar este voluntario? Se borrarán todos sus turnos.')">
                        <input type="hidden" name="id" value="<%=v.getId()%>" />
                        <button class="btn btn-danger btn-sm">Eliminar</button>
                    </form>
                <% } %>
            </td>
        </tr>
    <% } else { %>
        <tr><td colspan="6">No hay colaboradores</td></tr>
    <% } %>
    </tbody>
</table>
