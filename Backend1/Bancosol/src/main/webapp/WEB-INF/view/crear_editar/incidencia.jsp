<%@ page import="uma.grupo13.bancosol.entity.TurnoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="uma.grupo13.bancosol.dto.VoluntarioDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.UsuarioDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Incidencia</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
</head>
<%
    List<VoluntarioDTO> voluntarioDTOS = (List<VoluntarioDTO>) request.getAttribute("voluntarios");
    Integer idTurno = (Integer) request.getAttribute("idTurno");
    // CampanaDTO campana = (CampanaDTO) request.getAttribute("campana");
    //List<UsuarioDTO> admins = (List<UsuarioDTO>) request.getAttribute("admins");
%>
<body>
<main class="main-content">
   <header class = "header">
       <h1>Incidencia</h1>

       <div class="formulario">
            <form id="crear-incidencia" action="/turnos/reportar-incidencia" method="post">
                <input type="hidden" name="idNotificacion" value=""/>
                <div class="form-group">
                    <label for="asunto-incidencia">Especifique el asunto de la incidencia:</label>
                    <input type="text" name="asunto" id="asunto" required/>
                </div>

                <div class="form-group">
                    <label for="mensaje-incidencia">Especifique el motivo de la incidencia:</label>
                    <input type="text" name="mensaje" id="mensaje" required/>
                </div>

                <div class="form-group">
                    <label for="">Especifique los voluntarios/colaboradores implicados:</label>
                    <% for(VoluntarioDTO voluntarioAct: voluntarioDTOS){%>
                        <input type="checkbox" name="idsVoluntariosIncidencia" id="<%=voluntarioAct.getId()%>" value="<%=voluntarioAct.getId()%>"/>
                        <%=voluntarioAct.getNombre()%> <%= voluntarioAct.getApellidos()%>
                    <%}%>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Reportar incidencia</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>

       </div>
   </header>
</main>
</body>
</html>