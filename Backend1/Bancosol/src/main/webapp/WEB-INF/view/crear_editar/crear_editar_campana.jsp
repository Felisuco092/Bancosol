<%@ page import="uma.grupo13.bancosol.entity.CampanaEntity" %>
<%@ page import="uma.grupo13.bancosol.dto.CampanaDTO" %>
<%@ page import="uma.grupo13.bancosol.dto.CadenaDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<%
    CampanaDTO campana = (CampanaDTO) request.getAttribute("campana");
    if(campana == null) campana = new CampanaDTO();
    String error = (String) request.getAttribute("error");
    List<CadenaDTO> cadenas = (List<CadenaDTO>) request.getAttribute("cadenas");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=campana.getNombre() != null ? "Editar " : "Crear "%>campaña</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <link rel="stylesheet" href="../../../css/login.css">
</head>

<body>

    <main class="main-content">
        <header class="header">
            <h1><%= (campana.getId()!=null?"Editar ":"Crear ") %> campaña</h1>
            <% if (error != null) { %>
            <div class="alert-danger"><%= error %></div>
            <% } %>
        </header>

        <div class="formulario">
            <form id="form-crear-campana" action="/campanas/guardar" method ="post">
                <input type="hidden" name="idCampana" value="<%=campana.getId() == null?"":campana.getId()%>"/>

                <div class="form-group">
                    <label for="nombre">Especifique el nombre de la campaña:<span class="required">*</span></label>
                    <input type="text" name="nombre" id="nombre" value="<%=campana.getNombre() == null ? "":campana.getNombre()%>" required/>
                </div>
                <div class="form-group">
                    <label for="fecha-inicio">Especifique el día de comienzo de la campaña:<span class="required">*</span></label>
                    <input type="date" name="fecha-inicio" id="fecha-inicio" value="<%=campana.getDiaComienzo() == null ? "":campana.getDiaComienzo()%>" required/>
                </div>
                <div class="form-group">
                    <label for="fecha-fin">Especifique el día final de la campaña:<span class="required">*</span></label>
                    <input type="date" name="fecha-fin" id="fecha-fin" value="<%=campana.getDiaFinal() == null ? "":campana.getDiaFinal()%>" required/>
                </div>

                <% if (campana.getId() == null && cadenas != null && !cadenas.isEmpty()) { %>
                <div class="form-group">
                    <label>Cadenas que participan:</label>
                    <div class="checkbox-group" style="max-height: 150px; overflow-y: auto; border: 1px solid var(--input-border); padding: 10px; border-radius: 4px;">
                        <% for(CadenaDTO cadena : cadenas) { %>
                        <div class="checkbox-item" style="display: flex; align-items: center; margin-bottom: 0.5rem;">
                            <input type="checkbox" name="cadenasParticipantes" id="cad-<%=cadena.getId()%>" value="<%=cadena.getId()%>" style="width: auto; margin-right: 10px;">
                            <label for="cad-<%=cadena.getId()%>" style="margin-bottom: 0; display: inline; font-weight: normal;"><%=cadena.getNombre()%></label>
                        </div>
                        <% } %>
                    </div>
                </div>
                <% } %>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <%=campana.getId() == null ? "Crear ":"Editar "%>campaña
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
                </div>
            </form>
        </div>
    </main>
</body>
</html>