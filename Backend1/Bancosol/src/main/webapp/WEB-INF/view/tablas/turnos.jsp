<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page import="uma.grupo13.bancosol.dto.TurnoDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page import="uma.grupo13.bancosol.services.utils.Permiso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TurnoDTO> turnosList = (List<TurnoDTO>) request.getAttribute("turnos");
    String capitanNombre = (String) request.getAttribute("capitanNombre");
    Map<Permiso, Boolean> permisos = (Map<Permiso, Boolean>) session.getAttribute("permisos");
%>


    <table class="cuadrante-tabla" data-capitan="<%= capitanNombre != null ? capitanNombre : "" %>">
        <thead>
        <tr>
            <th>Día</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Campaña</th>
            <th>Voluntario Asignado</th>
            <th>Tienda a la que se ha asignado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="tabla-turnos-body">
        <%for(TurnoDTO turnoAct: turnosList){%>
            <tr>
            <td><%=turnoAct.getDia()%></td>
            <td><%=turnoAct.getHoraInicio()%></td>
            <td><%=turnoAct.getHoraFin()%></td>
            <td><%=turnoAct.getCampana().getNombre()%></td>
            <td>
                <%
                    VoluntarioDTO v = turnoAct.getVoluntario();
                    String nameToDisplay = (v != null && v.getNombreDisplay() != null) ? v.getNombreDisplay() : (v != null ? "Voluntario #" + v.getId() : "Sin asignar");
                %>
                <%= nameToDisplay %>
            </td>
            <td><%=turnoAct.getTienda().getDescripcion()%></td>
            <td>
                <% if (Boolean.TRUE.equals(permisos.get(Permiso.EDITAR_TURNOS))) { %>
                    <form action = "/turnos/borrar" method = "POST">
                        <input type="hidden" name="idTurno" value="<%=turnoAct.getId()%>"/>
                        <button class="btn btn-danger btn-sm">Borrar</button>
                    </form>
                <% } %>
                <% if (Boolean.TRUE.equals(permisos.get(Permiso.INCIDENCIAS))) { %>
                    <form action = "/turnos/incidencia" method = "POST">
                        <input type="hidden" name="idTurno" value="<%=turnoAct.getId()%>"/>
                        <button class="btn btn-info btn-incidence">Incidencia</button>
                    </form>
                <% } %>
            </td>
            </tr>
        <%}%>
        </tbody>
    </table>

