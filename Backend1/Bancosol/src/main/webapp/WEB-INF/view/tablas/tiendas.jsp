<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="uma.grupo13.bancosol.entity.TiendaEntity" %>
<%@ page import="uma.grupo13.bancosol.entity.ParticipaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.TiendaDTO" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page import="java.util.Map" %>
<%
    List<TiendaDTO> tiendas = (List<TiendaDTO>) request.getAttribute("tiendas");
    Integer idCampanaActual = (Integer) request.getAttribute("idCampanaActual");
    Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
%>
<table class="tabla-tiendas">
    <thead>
    <tr>
        <th>Tienda</th>
        <th>Localidad</th>
        <th>Domicilio</th>
        <th>C.P.</th>
        <th>Zona</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody id="tabla-tiendas-body">
    <%
        if (tiendas != null) {
            for(TiendaDTO t : tiendas){
                boolean participa = t.participaEn(idCampanaActual);
    %>
    <tr class="clickable">
        
        <td><%=t.getDescripcion()%></td>
        <td><%=t.getLocalidad()%></td>
        <td><%=t.getDomicilio()%></td>
        <td><%=t.getCPostal()%></td>
        <td><%=t.getZonaGeografica()%></td>
        <td>
        <% if(participa) { %>
                <span class="status-badge status-activa">Activa</span>
        <% } else { %>
                <span class="status-badge status-inactiva">Sin activar</span>
        <% } %>
        </td>
        <td>
            <% if (Boolean.TRUE.equals(permisos.get(Permiso.EDITAR_TIENDA))) { %>
                <a href="/tiendas/editar?id=<%=t.getId()%>"><button class="btn btn-primary btn-sm">Editar</button></a>
                <form action="/tiendas/borrar" method="POST" onsubmit="return confirm('¿Seguro que desea eliminar esta tienda?')">
                    <input type="hidden" name="id" value="<%=t.getId()%>">
                    <button type="submit" class="btn btn-danger btn-sm">Borrar</button>
                </form>
            <% } %>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
