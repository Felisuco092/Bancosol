<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es-ES">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Usuarios</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "usuarios"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Usuarios</h1>
            <a href="/usuarios/crear"><button class="btn btn-primary">+ Crear Usuario</button></a>
        </header>

        <div class="card">
            <jsp:include page="tablas/usuarios.jsp"/>
        </div>
    </main>
</body>
</html>
