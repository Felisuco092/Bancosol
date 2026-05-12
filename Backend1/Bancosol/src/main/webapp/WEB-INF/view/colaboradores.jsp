<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Colaboradores</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../../js/aside.js" defer></script>
    <script src="../../js/collaborators.js" type="module"></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "colaboradores"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Colaboradores</h1>
            <button class="btn btn-primary">+ Nuevo Colaborador</button>
        </header>

        <div class="card">
            <div class="filtros-grid">
                <div>
                    <label for="filter-tipo">Tipo:</label>
                    <select id="filter-tipo">
                        <option value="all">Todos</option>
                        <option value="false">Entidad / Grupo</option>
                        <option value="true">Persona Física</option>
                        <option value="confirmar">Por confirmar</option>
                    </select>
                </div>
                <div>
                    <label for="filter-localidad">Localidad:</label>
                    <select id="filter-localidad">
                        <option value="all">Todas</option>
                        <option value="Almáchar">Almáchar</option>
                        <option value="Rincón de la Victoria">Rincón de la Victoria</option>
                        <option value="Málaga">Málaga</option>
                    </select>
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Entidad / Nombre</th>
                        <th>Tipo</th>
                        <th>Localidad</th>
                        <th>C.P.</th>
                        <th>Voluntarios</th>
                        <th>Observaciones</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody id="colaboradores-tbody">
                    <!-- Datos ya procesados con js -->
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
