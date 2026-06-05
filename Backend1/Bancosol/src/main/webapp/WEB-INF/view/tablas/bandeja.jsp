<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.NotificacionDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<NotificacionDTO> notificacionList = (List<NotificacionDTO>) request.getAttribute("notificacionesList");
%>
<table id ="tabla-notificaciones">
    <thead>
    <tr>
        <th>Fecha</th>
        <th>Asunto</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <%for(NotificacionDTO notificacionAct: notificacionList){%>
    <tr>
        <td><%=notificacionAct.getFechaFormateada()%></td>
        <td><%=notificacionAct.getAsunto()%></td>
        <td>
            <form method="post" action="/bandeja/mensaje">
                <input type="hidden" name="idMensaje" value="<%=notificacionAct.getId()%>" />
                <button class="btn btn-primary btn-sm">
                    Ver mensaje
                </button>
            </form>
            <form action="/bandeja/delete" method="post">
                <input type="hidden" name="idMensaje" value="<%=notificacionAct.getId()%>" />
                <button class="btn btn-primary btn-sm">
                    Eliminar
                </button>
            </form>
        </td>
    </tr>
    <%}%>
    </tbody>
</table>