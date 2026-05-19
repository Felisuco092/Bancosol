<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<VoluntarioBaseEntity> cols = (List<VoluntarioBaseEntity>) request.getAttribute("colaboradores");
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
    <% if (cols != null) for (VoluntarioBaseEntity v : cols) {
        if (!(v instanceof VoluntarioFisicoEntity) && !(v instanceof VoluntarioEntidadEntity)) continue;
        boolean esPersona = v instanceof VoluntarioFisicoEntity;
        boolean pendiente = v.getAprobado() == null || !v.getAprobado();
        String badgeClass = pendiente ? "badge-confirmar" : (esPersona ? "badge-persona" : "badge-entidad");
        String badgeText = pendiente ? "Por confirmar" : (esPersona ? "Persona Física" : "Entidad / Grupo");
        String nombre;
        int nVol;
        if (esPersona) {
            VoluntarioFisicoEntity f = (VoluntarioFisicoEntity) v;
            nombre = f.getNombre() + " " + f.getApellidos();
            nVol = 1;
        } else {
            VoluntarioEntidadEntity e = (VoluntarioEntidadEntity) v;
            nombre = e.getNombreAsociacion();
            nVol = e.getNVoluntarios();
        }
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
                <a href="/colaboradores/editar?id=<%=v.getId()%>"><button class="btn btn-primary btn-sm">Editar</button></a>
                <form action="/colaboradores/borrar" method="post"
                      onsubmit="return confirm('¿Eliminar esta cadena? Se borrarán todas sus tiendas.')">
                    <input type="hidden" name="id" value="<%=v.getId()%>" />
                    <button class="btn btn-danger btn-sm">Eliminar</button>
                </form>
            </td>
        </tr>
    <% } else { %>
        <tr><td colspan="6">No hay colaboradores</td></tr>
    <% } %>
    </tbody>
</table>
