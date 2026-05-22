<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //Gemini ha hecho lo de hibernate unproxy
    List<TurnoEntity> turnosList = (List<TurnoEntity>) request.getAttribute("turnos");
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
        <%for(TurnoEntity turnoAct: turnosList){%>
            <tr>
            <td><%=turnoAct.getDia()%></td>
            <td><%=turnoAct.getHoraInicio()%></td>
            <td><%=turnoAct.getHoraFin()%></td>
            <td>
                <%
                String nameToDisplay = "Sin asignar";
                // try catch eliminado
                    VoluntarioBaseEntity v = turnoAct.getVoluntario();
                    if (v != null) {
                        Object actual = Hibernate.unproxy(v);
                        if (actual instanceof VoluntarioFisicoEntity) {
                            nameToDisplay = ((VoluntarioFisicoEntity) actual).getNombre();
                        } else if (actual instanceof VoluntarioEntidadEntity) {
                            nameToDisplay = ((VoluntarioEntidadEntity) actual).getNombreAsociacion();
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

