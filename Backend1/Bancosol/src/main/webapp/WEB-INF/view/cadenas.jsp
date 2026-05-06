<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Bandeja de Entrada</title>
    <link rel="stylesheet" href="../../css/styles.css">
</head>
<body>
<jsp:include page="aside.jsp"/>
    <main class="main-content">
        <header class="header">
            <h1>Gestión de Cadenas</h1>
            <button class="btn btn-primary">+ Nueva Cadena</button>
        </header>
        <div class="card">
            <jsp:include page="tablas/cadenas.jsp"/>
        </div>

    </main>
</body>