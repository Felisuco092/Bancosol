<%@ page import="uma.grupo13.bancosol.entity.NotificacionEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dao.NotificacionRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<NotificacionEntity> notificacionList = (List<NotificacionEntity>) request.getAttribute("notificacionesList");
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
    <%for(NotificacionEntity notificacionAct: notificacionList){%>
    <tr>
        <td><%=notificacionAct.getFechaCreacion()%></td>
        <td><%=notificacionAct.getAsunto()%></td>
        <td>
            <button class="btn btn-primary btn-view">Ver mensaje</button>
        </td>
    </tr>
    <%}%>
    </tbody>
</table>