<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page import="uma.grupo13.bancosol.dto.TurnoDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //Gemini ha hecho lo de hibernate unproxy
    List<TurnoDTO> turnosList = (List<TurnoDTO>) request.getAttribute("turnos");
    String capitanNombre = (String) request.getAttribute("capitanNombre");
%>


    <table class="cuadrante-tabla" data-capitan="<%= capitanNombre != null ? capitanNombre : "" %>">
        <thead>
        <tr>
            <th>Día</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Voluntario Asignado</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody id="tabla-turnos-body">
        <%for(TurnoDTO turnoAct: turnosList){%>
            <tr>
            <td><%=turnoAct.getDia()%></td>
            <td><%=turnoAct.getHoraInicio()%></td>
            <td><%=turnoAct.getHoraFin()%></td>
            <td>
                <%
                String nameToDisplay = "Sin asignar";
                // try catch eliminado
                    VoluntarioDTO v = turnoAct.getVoluntario();
                    if (v != null) {
                        if (v.getTipo()=="FISICO") {
                            nameToDisplay = v.getNombre();
                        } else if (v.getTipo()=="ENTIDAD") {
                            nameToDisplay = v.getNombreAsociacion();
                        } else {
                            nameToDisplay = "Voluntario #" + v.getId();
                        }
                    }
                %>
                <%= nameToDisplay %>
            </td>
            <td>
                <form action = "/turnos/borrar" method = "POST">
                    <input type="hidden" name="idTurno" value="<%=turnoAct.getId()%>"/>
                    <button class="btn btn-danger btn-sm">Borrar</button>
                </form>
                <form action = "/turnos/incidencia" method = "POST">
                    <input type="hidden" name="idTurno" value="<%=turnoAct.getId()%>"/>
                    <button class="btn btn-info btn-incidence">Incidencia</button>
                </form>
            </td>
            </tr>
        <%}%>
        </tbody>
    </table>

