<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CampanaEntity> campanasList = (List<CampanaEntity>) request.getAttribute("");
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
        <%for(CampanaEntity campanaAct: campanasList){%>
            <tr>
                <td><%=campanaAct.getNombre()%></td>
                <td><%=campanaAct.getAno()%></td>
                <td><%=campanaAct.getDiaComienzo()%></td>
                <td><%=campanaAct.getDiaFinal()%></td>
                <td>Por hacer </td>
                <td>
                    <button class="btn btn-primary btn-sm">Editar</button>
                    <button class="btn btn-danger btn-sm">Eliminar</button>
                </td>
            </tr>
        <%}%>
    </tbody>
</table>
