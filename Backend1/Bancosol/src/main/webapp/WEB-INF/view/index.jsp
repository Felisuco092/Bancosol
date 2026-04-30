<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script src="${pageContext.request.contextPath}/js/main.js" defer></script>

</head>
<body id="login-page" class="login-body">
    <div id="login-card" class="card login-card">
        <img src="${pageContext.request.contextPath}/assets/LOGO_BANCOSOL.png" alt="Logo BANCOSOL" id="login-logo" class="logo_login">
        <p class="login-subtitle">Acceso al sistema de gestión</p>
        <form id="login-form" action="/login" method="POST">
            <div id="login-input-username" class="input_login">
                <label for="username">Usuario:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div id="login-input-password" class="input_login">
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" id="login-submit-btn" class="btn btn-primary w-full">Entrar</button>
        </form>
    </div>
</body>
</html>
