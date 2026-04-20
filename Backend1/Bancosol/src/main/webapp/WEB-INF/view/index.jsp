<!-- ARCHIVO DE GESTIÓN DE LOS INICIOS DE SESIÓN-->
<%@ page import="java.util.List" %>
<!-- Hacer 'imports' necesarios en esta sección-->
<%--
  Created by IntelliJ IDEA.
  User: Germán
  Date: 20/04/2026
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BANCOSOL - Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../css/styles.css"> <!-- Importar css !-->
</head>

<body class="login-body">
  <div class = "card login-card">
    <h1 style="text-align: center;">Bancosol</h1>
    <p style="text-align: center;">Acceso al sistema de gestión</p>
      <form id="login-form" action="/login" method="POST">
          <div style="margin-bottom: 15px;">
              <label for="username">Usuario:</label>
              <input type="text" id="username" name="username" style="width: 100%; padding: 8px; margin-top: 5px;" required>
          </div>
          <div style="margin-bottom: 15px;">
              <label for="password">Contraseña:</label>
              <input type="password" id="password" name="password" style="width: 100%; padding: 8px; margin-top: 5px;" required>
          </div>
          <button type="submit" class="btn btn-primary" style="width: 100%;">Entrar</button>
      </form>
  </div>
</body>
</html>
