<%@ page import="uma.grupo13.bancosol.entity.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<TurnoEntity> turnosList = (List<TurnoEntity>) request.getAttribute("turnos");

%>
    <div class="card filtros-turnos">
        <div>
            <label for="select-campana">Campaña:</label>
            <select id="select-campana">
                <option value="">-- Seleccione Campaña --</option>
                <option value="1">Gran Recogida Primavera 2026</option>
                <option value="2">Campaña Navidad 2025</option>
            </select>
        </div>
        <div>
            <label for="select-tienda">Tienda:</label>
            <select id="select-tienda">
                <option value="">-- Seleccione Tienda --</option>
                <option value="101">Mercadona - Av. Andalucía</option>
                <option value="102">Carrefour - Rincón</option>
                <option value="103">Lidl - El Palo</option>
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
                    <td><%=turnoAct.getVoluntario()%></td>
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
