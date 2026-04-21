<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 20/04/2026
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Bandeja de notificaciones</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
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
                <th>Estado</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>14/04/2026</td>
                <td>Nueva campaña "Gran Recogida" asignada</td>
                <td><strong>No leído</strong></td>
            </tr>
            <tr>
                <td>12/04/2026</td>
                <td>Cambio en el turno de Sábado - Tienda Almáchar</td>
                <td>Leído</td>
            </tr>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
