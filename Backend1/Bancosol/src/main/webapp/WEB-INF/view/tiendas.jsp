<%--
  Created by IntelliJ IDEA.
  User: Germán
  Date: 20/04/2026
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BANCOSOL - Tiendas</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<aside class="sidebar">
    <h2>Bancosol</h2>
    <nav>
        <ul>
            <li><a href="dashboard.html">Cuadro de Mando</a></li>
            <li><a href="campanas.html">Gestión de Campañas</a></li>
            <li><a href="tiendas.html" class="active">Gestión de Tiendas</a></li>
            <li><a href="colaboradores.html">Colaboradores</a></li>
            <li><a href="usuarios.html">Usuarios</a></li>
            <li><a href="turnos.html">Asignación de Turnos</a></li>
            <li><a href="bandeja.html">Bandeja de Entrada</a></li>
            <li><a href="#" id="logout-btn" style="color: #ff6b6b;">Cerrar Sesión</a></li>
        </ul>
    </nav>
</aside>
</body>
</html>
