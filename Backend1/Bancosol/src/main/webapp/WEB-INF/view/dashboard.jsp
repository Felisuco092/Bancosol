<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 20/04/2026
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Dashboard</title>
    <link rel="stylesheet" href="/css/styles.css">
</head>
<body>
<aside class="sidebar">
    <h2>Bancosol</h2>
    <nav>
        <ul>
            <li><a href="dashboard" class="active">Cuadro de Mando</a></li>
            <li><a href="campanas">Gestión de Campañas</a></li>
            <li><a href="tiendas">Gestión de Tiendas</a></li>
            <li><a href="colaboradores">Colaboradores</a></li>
            <li><a href="usuarios">Usuarios</a></li>
            <li><a href="turnos">Asignación de Turnos</a></li>
            <li><a href="bandeja">Bandeja de Entrada</a></li>
            <li><a href="#" id="logout-btn" style="color: #ff6b6b;">Cerrar Sesión</a></li>
        </ul>
    </nav>
</aside>

<main class="main-content">
    <header class="header">
        <h1>Cuadro de Mando</h1>
        <span>Bienvenido, Administrador</span>
    </header>

    <div class="card">
        <h3>Resumen de Cobertura</h3>
        <p>Gráficas de barras e índices de cobertura por cadenas y localidades (Referencia PDF pág. 9).</p>
        <div style="height: 200px; background: #eee; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
            [Espacio para Gráfica de Barras]
        </div>
    </div>

    <div style="display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px;">
        <div class="card">
            <h4>Total Tiendas</h4>
            <p style="font-size: 2rem; margin: 0;">152</p>
        </div>
        <div class="card">
            <h4>Voluntarios</h4>
            <p style="font-size: 2rem; margin: 0;">2,450</p>
        </div>
        <div class="card">
            <h4>Cobertura Media</h4>
            <p style="font-size: 2rem; margin: 0;">85%</p>
        </div>
    </div>
</main>
</body>
</html>
