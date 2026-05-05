<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Bandeja de Entrada</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "bandeja"); %>
    <jsp:include page="aside.jsp"/>
    <div id="tabla-bandeja-notificaciones">
        <jsp:include page="tablas/bandeja.jsp" /> <!-- Añadir referencia a la tabla que mostramos -->
    </div>

</body>
</html>
