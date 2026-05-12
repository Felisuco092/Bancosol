<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Bandeja de Entrada</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "bandeja"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Bandeja de Entrada</h1>
        </header>
        <div class="card">
            <jsp:include page="tablas/bandeja.jsp" /> <!-- Añadir referencia a la tabla que mostramos -->
        </div>
    </main>

</body>
</html>
