<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Asignación de Turnos</title>
    <link rel="stylesheet" href="../../css/styles.css">
    <script src="../js/aside.js" defer></script>
    <script src="../js/turnos.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "turnos"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Asignación de Turnos</h1>
        </header>

        <div class="card filtros-turnos">
            <div>
                <label for="select-campana">Campaña:</label>
                <select id="select-campana">
                    <option value="">-- Seleccione Campaña --</option>
                    <option value="1">Gran Recogida Primavera 2026</option>
                    <option value="2">Campaña Navidad 2025</option>
                </select>
            </div>
            <div>
                <label for="select-tienda">Tienda:</label>
                <select id="select-tienda">
                    <option value="">-- Seleccione Tienda --</option>
                    <option value="101">Mercadona - Av. Andalucía</option>
                    <option value="102">Carrefour - Rincón</option>
                    <option value="103">Lidl - El Palo</option>
                </select>
            </div>
            <button id="btn-buscar" class="btn btn-primary">Ver Cuadrante</button>
        </div>

        <div id="cuadrante-container">
            <div class="card">
                <div class="cuadrante-header">
                    <h3>Cuadrante de Turnos</h3>
                    <div class="cuadrante-actions">
                        <span>Capitán: <strong id="capitan-nombre">Juan Pérez</strong></span>
                        <button class="btn btn-success btn-add-extra">+ Añadir Turno Extra</button>
                    </div>
                </div>
                
                <table class="cuadrante-tabla">
                    <thead>
                        <tr>
                            <th>Día</th>
                            <th>Inicio</th>
                            <th>Fin</th>
                            <th>Voluntarios Asignados</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="tabla-turnos-body">
                        <!-- Filas de ejemplo -->
                        <tr>
                            <td>Viernes 01/04</td>
                            <td>09:00</td>
                            <td>15:00</td>
                            <td>
                                <div class="voluntarios-cell">
                                    <span class="voluntario-tag">Carlos Ruiz <button class="btn-remove">×</button></span>
                                    <span class="voluntario-tag">Ana Belén <button class="btn-remove">×</button></span>
                                    <button class="btn btn-sm btn-add">+ Añadir</button>
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-danger btn-incidence">Incidencia</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>
