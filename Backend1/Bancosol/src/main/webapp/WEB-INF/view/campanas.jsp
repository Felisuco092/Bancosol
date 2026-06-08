<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Campañas</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "campanas"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Campañas</h1>
            <button class="btn btn-primary" onclick="location.href='/campanas/crear'">+ Nueva Campaña</button>
        </header>

        <div class="card filtros-campanas">
            <button class="btn filter-btn active" data-filter="all">Todas</button>
            <button class="btn filter-btn" data-filter="activa">Activas</button>
            <button class="btn filter-btn" data-filter="terminada">Terminadas</button>
            <button class="btn filter-btn" data-filter="proximamente">Próximamente</button>
        </div>

        <div class="card">
            <jsp:include page="tablas/campana.jsp"/>
        </div>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('.filter-btn').forEach(button => {
                button.addEventListener('click', () => {
                    const filter = button.getAttribute('data-filter');
                    document.querySelectorAll('.filter-btn').forEach(btn => btn.classList.remove('active'));
                    button.classList.add('active');
                    document.querySelectorAll('.campaign-row').forEach(row => {
                        const status = row.getAttribute('data-status');
                        row.style.display = (filter === 'all' || status === filter) ? '' : 'none';
                    });
                });
            });
        });
    </script>
</body>
</html>
