<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CampanaEntity> campanasList = (List<CampanaEntity>) request.getAttribute("campanas");
    LocalDate today = LocalDate.now();
%>
<table>
    <thead>
    <tr>
        <th><b>Nombre de Campaña</b></th>
        <th><b>Año</b></th>
        <th><b>Inicio</b></th>
        <th><b>Fin</b></th>
        <th><b>Estado</b></th>
        <th><b>Acciones</b></th>
    </tr>
    </thead>
    <tbody id="campaign-table-body">
        <% if (campanasList != null) { 
            for(CampanaEntity campanaAct: campanasList){ 
                String status = "activa";
                String label = "Activa";
                String style = "background: #d4edda; color: #155724;";

                if (today.isBefore(campanaAct.getDiaComienzo())) {
                    status = "proximamente";
                    label = "Próximamente";
                    style = "background: #fff3cd; color: #856404;";
                } else if (today.isAfter(campanaAct.getDiaFinal())) {
                    status = "terminada";
                    label = "Terminada";
                    style = "background: #f8d7da; color: #721c24;";
                }
        %>
            <tr class="campaign-row" data-status="<%=status%>">
                <td><%=campanaAct.getNombre()%></td>
                <td><%=campanaAct.getAno()%></td>
                <td><%=campanaAct.getDiaComienzo()%></td>
                <td><%=campanaAct.getDiaFinal()%></td>
                <td class="status-cell">
                    <span style="<%=style%> padding: 4px 8px; border-radius: 4px; font-size: 0.85rem;">
                        <%=label%>
                    </span>
                </td>
                <td>
                    <a href="/campanas/editar?id=<%=campanaAct.getId()%>"><button class="btn btn-primary btn-sm">Editar</button></a>
                    <form action="/campanas/borrar" method="post" style="display:inline;"
                        onsubmit="return confirm('¿Está seguro de eliminar esta campaña?')">
                        <input type="hidden" name="id" value="<%=campanaAct.getId()%>">
                        <button class="btn btn-danger btn-sm">Eliminar</button>
                    </form>
                </td>
            </tr>
        <%  } 
        } else { %>
            <tr>
                <td colspan="6">No hay campañas disponibles</td>
            </tr>
        <% } %>
    </tbody>
</table>
