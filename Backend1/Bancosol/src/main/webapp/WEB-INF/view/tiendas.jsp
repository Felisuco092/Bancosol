<%--
  Created by IntelliJ IDEA.
  User: Germán
  Date: 20/04/2026
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(session.getAttribute("user") == null){
        response.sendRedirect("index.jsp");
        return;
    }
%>
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
<main class = "main-content">
    <header class="header">
        <h1>Gestión de Tiendas</h1>
        <div class="filters">
            <input type="text" placeholder="Buscar por cadena, localidad..." style="padding: 8px; width: 300px;">
        </div>
    </header>

    <div style="display: flex; gap: 20px;">
        <div class="card" style="flex: 2;">
            <h3>Listado de Tiendas</h3>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Cadena</th>
                    <th>Localidad</th>
                    <th>Coordinador</th>
                    <th>Participación</th>
                </tr>
                </thead>
                <tbody>
                <tr onclick="cargarDetalle('CARR-01')">
                    <td>CARR-01</td>
                    <td>Carrefour Hiper</td>
                    <td>Rincón de la Victoria</td>
                    <td>JM Cobos</td>
                    <td><span class="badge active">✔</span></td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="card" style="flex: 1;">
            <h3>Detalle de Tienda (Formulario)</h3>
            <form action="${pageContext.request.contextPath}/tiendas/update" method="POST">
                <label>Domicilio:</label>
                <input type="text" name="domicilio" value="c/ Arroyo de Totalán, nº 36" style="width: 100%; margin-bottom: 10px; padding: 5px;">

                <label>C. Postal:</label>
                <input type="text" name="cp" value="29730" style="width: 50%; margin-bottom: 10px; padding: 5px;">

                <label>Coordinador Asignado:</label>
                <select name="id_coordinador" style="width: 100%; margin-bottom: 10px; padding: 5px;">
                    <option value="1">JM Cobos</option>
                    <option value="2">Ana López</option>
                </select>

                <div style="margin: 15px 0;">
                    <label><input type="checkbox" name="excepcion"> Marcar Excepción</label>
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%;">Actualizar Tienda</button>
            </form>
        </div>
    </div>
</main>
</body>
</html>
