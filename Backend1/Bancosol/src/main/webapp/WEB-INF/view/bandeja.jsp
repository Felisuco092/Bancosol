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

    <main class="main-content">
        <header class="header">
            <h1>Bandeja de Entrada</h1>
        </header>

        <div class="card">
            <h3>Notificaciones</h3>
            <table>
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Asunto</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>14/04/2026</td>
                        <td>Nueva campaña "Gran Recogida" asignada</td>
                        <td>
                            <button class="btn btn-primary btn-view">Ver mensaje</button>
                        </td>
                    </tr>
                    <tr>
                        <td>12/04/2026</td>
                        <td>Cambio en el turno de Sábado - Tienda Almáchar</td>
                        <td>
                            <button class="btn btn-primary btn-view">Ver mensaje</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
