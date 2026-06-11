<%--
Página JSP que muestra la informacion de lo mensajes

Autores:
- German Pelaez Gallardo: 30%
- Félix Jiménez Almanza: 66%
- IA Generativa: 4% (El style que respeta los saltos de linea)

--%>
<%@ page import="uma.grupo13.bancosol.dto.NotificacionDTO" %>

<%@ page import="org.aspectj.weaver.ast.Not" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    NotificacionDTO notificacion = (NotificacionDTO) request.getAttribute("notificacion");
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ver Mensaje</title>
    <link rel="stylesheet" href="../../../css/styles.css">
    <link rel="stylesheet" href="../../../css/formulario.css">
    <link rel="icon" type="image/png" href="/assets/LOGO_BANCOSOL_ICONO.png">

</head>
<body>
<main class="main-content">
    <header class="header">
        <h1>Mensaje | <%=notificacion.getAsunto()%></h1>
    </header>

    <div class="formulario">
        <div class="form-group">
            <label for="nombre">Mensaje:</label>
            <!-- Para que respete los saltos de línea -->
            <p style="white-space: pre-line;"><%=notificacion.getMensaje()%></p>
        </div>
        <div class="form-actions">
            <button type="button" class="btn btn-secondary" onclick="history.back()">Volver</button>
        </div>
    </div>
</main>
</body>
</html>

