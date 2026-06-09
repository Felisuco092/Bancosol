<%--
Página JSP que muestra el formulario para crear turno

Autores:
- German Pelaez Gallardo: 80%
- IA Generativa: 20% (El <script> nos ha ayudado la IA)

--%>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Hibernate" %>
<%@ page import="uma.grupo13.bancosol.entity.*" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.TiendaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página-crear-turno</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
</head>
<%
    List<CampanaDTO> campanas = (List<CampanaDTO>) request.getAttribute("campanas");
    List<TiendaDTO> tiendas = (List<TiendaDTO>) request.getAttribute("tiendas");
    List<VoluntarioDTO> voluntarios = (List<VoluntarioDTO>) request.getAttribute("voluntarios");
    String error = (String) request.getAttribute("error");
    String tipoTurno = (String) request.getAttribute("tipoTurno");
    String dia = (String) request.getAttribute("dia");
    String horaInicio = (String) request.getAttribute("horaInicio");
    String horaFin = (String) request.getAttribute("horaFin");
    Integer idCampanaSel = (Integer) request.getAttribute("idCampanaSel");
    Integer idTiendaSel = (Integer) request.getAttribute("idTiendaSel");
    Integer idVolunarioSel =(Integer) request.getAttribute("idVoluntarioSel");
%>
<body>
    <main class="main-content">
        <header class="header">
            <h1><b>Crear Turno</b></h1>
        </header>

        <% if (error != null) { %>
            <div style="color: red; background-color: #fee;">
                <%= error %>
            </div>
        <% } %>

        <div class="formulario">
            <form id= "form-crear-turno" action="/turnos/guardar" method="post">
                <div class="form-group">
                    <label for="idCampana">Campaña:<span class="required">*</span></label>
                    <select name="idCampana" id="idCampana" required>
                        <option value="">-- Seleccione Campaña --</option>
                        <% for(CampanaDTO c : campanas) { %>
                            <option value="<%=c.getId()%>" <%= (idCampanaSel != null && idCampanaSel.equals(c.getId())) ? "selected" : "" %>><%=c.getNombre()%>-<%=c.getAno()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="idTienda">Tienda:<span class="required">*</span></label>
                    <select name="idTienda" id="idTienda" required>
                        <option value="">-- Seleccione Tienda --</option>
                        <% for(TiendaDTO t : tiendas) { %>
                            <option value="<%=t.getId()%>" <%= (idTiendaSel != null && idTiendaSel.equals(t.getId())) ? "selected" : "" %>><%=t.getDescripcion()%></option>
                        <% } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="idVoluntario">Voluntario:<span class="required">*</span></label>
                    <select name="idVoluntario" id="idVoluntario" required>
                        <option value="">-- Seleccione Voluntario --</option>
                        <% for(VoluntarioDTO v: voluntarios){
                            if (Boolean.TRUE.equals(v.getAprobado())) {
                        %>
                        <option value="<%=v.getId()%>" <%= (idVolunarioSel != null && idVolunarioSel.equals(v.getId())) ? "selected" : "" %>>
                            <%= v.getNombreDisplay() != null ? v.getNombreDisplay() : ("Voluntario #" + v.getId()) %>
                        </option>
                        <% }} %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipo-turno">Especifica el tipo de turno:<span class="required">*</span></label>
                    <input type="text" name="tipo-turno" id="tipo-turno" required value="<%= tipoTurno != null ? tipoTurno : "" %>"/>
                </div>
                <div class="form-group">
                    <label for="dia">Especifica el día del turno:<span class="required">*</span></label>
                    <input type="date" name="dia" id="dia" required value="<%= dia != null ? dia : "" %>"/>
                </div>
                <div class="form-group">
                    <label for="hora-inicio">Especifique la hora de comienzo del turno:<span class="required">*</span></label>
                    <input type="time" name="hora-inicio" id="hora-inicio" required value="<%= horaInicio != null ? horaInicio : "" %>"/>
                </div>
                <div class="form-group">
                    <label for="hora-fin">Especifique la hora final del turno:<span class="required">*</span></label>
                    <input type="time" name="hora-fin" id="hora-fin" required value="<%= horaFin != null ? horaFin : "" %>"/>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Crear y guardar turno</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>

    </main>

    <script>
        const filterTipo = document.getElementById('idCampana');
        const filterTienda = document.getElementById('idTienda');
        const originalStoreOptions = filterTienda.innerHTML;

        function actualizarTiendas() {
            const idCampana = filterTipo.value;

            if (idCampana) {
                fetch('/turnos/tiendas-por-campana?idCampana=' + idCampana)
                    .then(r => r.json())
                    .then(tiendas => {
                        filterTienda.innerHTML = '';
                        const defaultOpt = document.createElement('option');
                        defaultOpt.value = '';
                        defaultOpt.textContent = '-- Seleccione Tienda --';
                        filterTienda.appendChild(defaultOpt);
                        tiendas.forEach(t => {
                            const opt = document.createElement('option');
                            opt.value = t.id;
                            opt.textContent = t.descripcion;
                            filterTienda.appendChild(opt);
                        });
                    })
                    .catch(error => console.error(error));
            } else {
                filterTienda.innerHTML = originalStoreOptions;
                filterTienda.value = '';
            }
        }

        filterTipo.addEventListener('change', actualizarTiendas);

        if (filterTipo.value) {
            actualizarTiendas();
        }
    </script>
</body>
</html>
