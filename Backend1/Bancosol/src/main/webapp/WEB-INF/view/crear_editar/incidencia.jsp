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
    List<VoluntarioDTO> voluntariosDTOIncidencia = (List<VoluntarioDTO>) request.getAttribute("voluntariosDTOIncidencia");
    // CampanaDTO campana = (CampanaDTO) request.getAttribute("campana");
    //List<UsuarioDTO> admins = (List<UsuarioDTO>) request.getAttribute("admins");
%>
<body>
<main class="main-content">
   <header class = "header">
       <h1>Reporte de Incidencia</h1>
   </header>

       <div class="formulario">
            <form id="crear-incidencia" action="/turnos/reportar-incidencia" method="post">
                <input type="hidden" name="idTurno" value="<%=idTurno%>"/>
                <div class="form-group">
                    <label for="asunto-incidencia">Especifique el asunto de la incidencia:<span class="required">*</span></label>
                    <input type="text" name="asunto" id="asunto" required/>
                </div>

                <div class="form-group">
                    <label for="mensaje-incidencia">Especifique el motivo de la incidencia:<span class="required">*</span></label>
                    <input type="text" name="mensaje" id="mensaje" required/>
                </div>

                <div class="form-group">
                    <label>Especifique los voluntarios/colaboradores implicados:</label>
                    <div class="checkbox-group" style="max-height: 200px; overflow-y: auto; border: 1px solid var(--input-border); padding: 10px; border-radius: 4px;">
                    <% for(VoluntarioDTO voluntarioAct: voluntarioDTOS){
                        boolean checked = false;
                        if (voluntariosDTOIncidencia != null) {
                            for (VoluntarioDTO v : voluntariosDTOIncidencia) {
                                if (v.getId().equals(voluntarioAct.getId())) {
                                    checked = true;
                                    break;
                                }
                            }
                        }
                    %>
                    <div class="checkbox-item" style="display: flex; align-items: center; margin-bottom: 0.5rem;">
                        <input type="checkbox" name="idsVoluntariosIncidencia" id="vol-<%=voluntarioAct.getId()%>" value="<%=voluntarioAct.getId()%>" <%=checked ? "checked" : ""%> style="width: auto; margin-right: 10px;">
                        <label for="vol-<%=voluntarioAct.getId()%>" style="margin-bottom: 0; display: inline; font-weight: normal;"><%=voluntarioAct.getNombreDisplay()%></label>
                    </div>
                    <%}%>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Reportar incidencia</button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>

       </div>

</main>
</body>
</html>