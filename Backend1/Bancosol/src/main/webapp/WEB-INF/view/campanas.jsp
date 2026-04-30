<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Campañas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/campanas.js" type="module"></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "campanas"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Campañas</h1>
            <button class="btn btn-primary">+ Nueva Campaña</button>
        </header>

        <div class="card filtros-campanas">
            <button class="btn filter-btn active" data-filter="all">Todas</button>
            <button class="btn filter-btn" data-filter="activa">Activas</button>
            <button class="btn filter-btn" data-filter="terminada">Terminadas</button>
            <button class="btn filter-btn" data-filter="proximamente">Próximamente</button>
        </div>

        <div class="card">
            <table>
                <thead>
                    <tr>
                        <th>Nombre de Campaña</th>
                        <th>Año</th>
                        <th>Inicio</th>
                        <th>Fin</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody id="campaign-table-body">
                    <!-- Datos cargados por JS -->
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
