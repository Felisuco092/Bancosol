<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bancosol - Tiendas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/tiendas.js" defer></script>
    <script src="${pageContext.request.contextPath}/js/main.js" defer></script>
</head>
<body>
    <% request.setAttribute("paginaActual", "tiendas"); %>
    <jsp:include page="aside.jsp"/>

    <main class="main-content">
        <header class="header">
            <h1>Gestión de Tiendas</h1>
            <button class="btn btn-primary" onclick="location.href='crear_tienda.html'">+ Nueva Tienda</button>
        </header>

        <div class="card">
            <div class="filtros-grid">
                <div>
                    <label for="select-filtro-campanas">Campaña:</label>
                    <select id="select-filtro-campanas">
                        <option value="campana-gran-recogida">Campaña de Gran Recogida Primavera</option>
                        <option value="campana-navidad">Campaña Navidad</option>
                        <option value="operacion-kilo" selected>Operación Kilo Junio</option>
                    </select>
                </div>
                <div>
                    <label for="filtro-por-cadenas">Cadena:</label>
                    <select id="filtro-por-cadenas">
                        <option value="todas">Seleccionar Cadena...</option>
                        <option value="mercadona">Mercadona</option>
                        <option value="lidl">Lidl</option>
                        <option value="dia">Dia</option>
                        <option value="aldi">Aldi</option>
                        <option value="coviran">Covirán</option>
                        <option value="corte-ingles">El Corte Inglés</option>
                        <option value="carrefour">Carrefour</option>
                    </select>
                </div>
                <div>
                    <label for="filtro-por-localidad">Localidad:</label>
                    <select id="filtro-por-localidad">
                        <option value="todas">Seleccionar Localidad...</option>
                        <option value="malaga">Málaga</option>
                        <option value="marbella">Marbella</option>
                        <option value="antequera">Antequera</option>
                    </select>
                </div>
            </div>

            <table class="tabla-tiendas">
                <thead>
                    <tr>
                        <th>Tienda</th>
                        <th>Participación</th>
                        <th>Localidad</th>
                        <th>Domicilio</th>
                        <th>C.P.</th>
                        <th>Zona</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody id="tabla-tiendas-body">
                    <!-- Filas estáticas del HTML original, se podrían dinamizar después -->
                    <tr class="clickable" data-cadena="mercadona" data-localidad="malaga">
                        <td>Mercadona el Cónsul</td>
                        <td><input type="checkbox" class="check-participa"></td>
                        <td>MÁLAGA</td>
                        <td>c/ Aristófanes, nºxx</td>
                        <td>29010</td>
                        <td>Teatinos</td>
                        <td><span class="status-badge status-inactiva">Sin activar</span></td>
                        <td>
                            <button class="btn btn-primary btn-sm">Editar</button>
                            <button class="btn btn-danger btn-sm">Borrar</button>
                        </td>
                    </tr>
                    <!-- ... resto de filas ... -->
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
