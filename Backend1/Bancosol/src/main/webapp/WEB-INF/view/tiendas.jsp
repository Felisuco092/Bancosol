<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Tiendas</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "tiendas"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Tiendas</h1>
            <a href="/tiendas/crear"><button class="btn btn-primary">+ Crear Tienda</button></a>
        </header>

        <div class="card">
            <div class="filtros-grid">
                <jsp:include page="filtros/tiendas.jsp"/>
            </div>
            <jsp:include page="tablas/tiendas.jsp"/>
        </div>
    </main>
</body>
</html>
