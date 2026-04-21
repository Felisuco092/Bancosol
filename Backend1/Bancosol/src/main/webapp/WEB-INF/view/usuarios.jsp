<%--
  Created by IntelliJ IDEA.
  User: Germán
  Date: 20/04/2026
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>BANCOSOL - Usuarios</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <jsp:include page="aside.jsp"/>
    <main class="main-content">
        <header class="header">
            <h1>Gestión de Usuarios</h1>
            <button class="btn btn-primary">+ Crear Usuario</button>
        </header>

        <div class="card">
            <h3>Alta de Coordinadores / Capitanes (Referencia PDF pág. 5)</h3>
            <form>
                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 20px;">
                    <div>
                        <label>Nombre:</label>
                        <input type="text" style="width: 100%; padding: 8px;">
                    </div>
                    <div>
                        <label>Zona Geográfica:</label>
                        <input type="text" placeholder="Ej: Axarquía" style="width: 100%; padding: 8px;">
                    </div>
                    <div>
                        <label>Rol:</label>
                        <select style="width: 100%; padding: 8px;">
                            <option>Administrador</option>
                            <option>Coordinador</option>
                            <option>Capitán</option>
                        </select>
                    </div>
                    <div>
                        <label>Contraseña:</label>
                        <input type="password" style="width: 100%; padding: 8px;">
                    </div>
                </div>
                <button type="submit" class="btn btn-success" style="margin-top: 20px;">Crear Usuario</button>
            </form>
        </div>
    </main>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
