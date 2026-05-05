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
<jsp:include page="aside.jsp"/>
    <main class="main-content">
        <header class="header">
            <h1>Gestión de Cadenas</h1>
            <button class="btn btn-primary">+ Nueva Cadena</button>
        </header>
        <div class="card">
            <div class="filtros-grid">
                <div>
                    <label for="select-filtro-campanas">Campaña:</label>
                    <select id="select-filtro-campanas">
                        <option value="">Seleccionar Campaña...</option>
                    </select>
                </div>
                <div class="filtros-cadenas">
                    <label>Mostrar:</label>
                    <div class="btn-group">
                        <button class="btn filter-btn active" data-filter="all">Todas</button>
                        <button class="btn filter-btn" data-filter="activa">Activas</button>
                    </div>
                </div>
            </div>
            <!-- Mudar estilo al css más adelante -->
            <div id="cadenas-placeholder" class="text-center" style="padding: 2rem; color: #666;">
                Seleccione una campaña para visualizar las cadenas
            </div>

            <jsp:include page="tablas/cadenas.jsp"/>
        </div>

    </main>
</body>