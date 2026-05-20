<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //Gemini ha hecho lo de hibernate unproxy
    List<TurnoEntity> turnosList = (List<TurnoEntity>) request.getAttribute("turnos");
    List<TiendaEntity> tiendasList = (List<TiendaEntity>) request.getAttribute("tiendas");
    List<CampanaEntity> campanasList = (List<CampanaEntity>) request.getAttribute("campanas");

%>
    <div class="card filtros-turnos">
        <div>
            <label for="select-campana">Campaña:</label>
            <select id="select-campana">
                <option value="">-- Seleccione Campaña --</option>

                <%
                int i = 1;
                for(CampanaEntity campanaAct: campanasList){%>
                    <option value="<%=i%>"><%=campanaAct.getNombre()%>-<%=campanaAct.getAno()%></option>
                <%}%>
            </select>
        </div>
        <div>
            <label for="select-tienda">Tienda:</label>
            <select id="select-tienda">
                <option value="">-- Seleccione Tienda --</option>
                <%for(TiendaEntity tiendaAct: tiendasList){%>
                    <option value="<%=tiendaAct.getId()%>"><%=tiendaAct.getDescripcion()%></option>
                <%}%>
            </select>
        </div>
        <button id="btn-buscar" class="btn btn-primary">Ver Cuadrante</button>
    </div>

    <div id="cuadrante-container">
        <div class="card">
            <div class="cuadrante-header">
                <h3>Cuadrante de Turnos</h3>
                <div class="cuadrante-actions">
                    <span>Capitán: <strong id="capitan-nombre"></strong></span>
                    <a href="/turnos/crear">
                        <button class="btn btn-success btn-add-extra">+ Añadir Turno Extra</button>
                    </a>
                </div>
            </div>
            <table class="cuadrante-tabla">
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
                            <button class="btn btn-danger btn-incidence">Incidencia</button>
                        </form>
                    </td>
                    </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
