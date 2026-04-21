<%--
  Created by IntelliJ IDEA.
  User: felix
  Date: 20/04/2026
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bancosol - Colaboradores</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<jsp:include page="aside.jsp"/>

<main class="main-content">
    <header class="header">
        <h1>Gestión de Colaboradores</h1>
        <button class="btn btn-primary">+ Nuevo Grupo/Entidad</button>
    </header>

    <div class="card">
        <h3>Alta de Colaboradores (Referencia PDF pág. 4)</h3>
        <form>
            <div style="margin-bottom: 15px;">
                <label>Nombre del Grupo/Entidad:</label>
                <input type="text" placeholder="Ej: Ayuntamiento de Almáchar" style="width: 100%; padding: 8px;">
            </div>

            <h4>Contactos (Máximo 3)</h4>
            <div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 10px;">
                <label>Contacto 1:</label>
                <input type="text" placeholder="Nombre" style="width: 30%;">
                <input type="text" placeholder="Teléfono" style="width: 30%;">
                <input type="email" placeholder="Email" style="width: 30%;">
            </div>

            <button type="submit" class="btn btn-success">Registrar Colaborador</button>
        </form>
    </div>

    <div class="card">
        <h3>Listado de Colaboradores</h3>
        <table>
            <thead>
            <tr>
                <th>Entidad</th>
                <th>Contacto Principal</th>
                <th>Teléfono</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Parroquia San Juan</td>
                <td>Padre Mateo</td>
                <td>952000000</td>
                <td><button>Ver</button></td>
            </tr>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
