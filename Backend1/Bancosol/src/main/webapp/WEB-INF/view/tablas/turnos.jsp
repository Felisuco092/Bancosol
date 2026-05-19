<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TurnoEntity> turnosList = (List<TurnoEntity>) request.getAttribute("turnos");
%>
<table class="cuadrante-tabla">
    <thead>
    <tr>
        <th>Día</th>
        <th>Inicio</th>
        <th>Fin</th>
        <th>Voluntarios Asignados</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody id="tabla-turnos-body">
    <%for(TurnoEntity turnoAct: turnosList){%>
        <tr>
        <td><%=turnoAct.getDia()%></td>
        <td><%=turnoAct.getHoraInicio()%></td>
        <td><%=turnoAct.getHoraFin()%></td>
        <td>
            <button class="btn btn-danger btn-incidence">Incidencia</button>
        </td>
        </tr>
    <%}%>
    </tbody>
</table>